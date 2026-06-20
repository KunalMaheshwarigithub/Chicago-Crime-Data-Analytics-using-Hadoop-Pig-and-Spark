from pyspark.sql import SparkSession
from pyspark.sql.window import Window
import pyspark.sql.functions as F
import os

# Initialize Spark in crash-proof local mode
spark = SparkSession.builder \
    .appName("Chicago Crime - YoY Percentage Change") \
    .master("local[*]") \
    .getOrCreate()

# 1. Load the dataset dynamically from your local working directory
current_dir = os.getcwd()
input_path = f"file://{current_dir}/local_test_data.csv"
print(f"Reading from: {input_path}")
df = spark.read.option("header", "true").csv(input_path)

# Verify parsed headers in log outputs
print("Columns found in dataset:", df.columns)

# 2. Extract crime types and years cleanly using robust column targeting
year_col = "Year" if "Year" in df.columns else df.columns[9]
type_col = "Primary Type" if "Primary Type" in df.columns else df.columns[3]

crime_counts = df.filter(F.col(year_col).isNotNull()) \
                 .filter(F.col(year_col).rlike(r"^\d{4}$")) \
                 .groupBy(F.col(type_col).alias("PrimaryType"), F.col(year_col).alias("Year")) \
                 .agg(F.count("ID").alias("CurrentYearCount"))

# 3. Create a Window specification partitioned by Crime Type and ordered by Year
window_spec = Window.partitionBy("PrimaryType").orderBy("Year")

# 4. Use lag() to pull the previous year's count into the current row context
yoy_df = crime_counts.withColumn("PrevYearCount", F.lag("CurrentYearCount", 1).over(window_spec))

# 5. Compute the exact percentage change formula
final_yoy = yoy_df.withColumn(
    "PctChange",
    F.when(F.col("PrevYearCount").isNull(), None) \
     .otherwise(F.round(((F.col("CurrentYearCount") - F.col("PrevYearCount")) / F.col("PrevYearCount")) * 100, 2))
).select("PrimaryType", "Year", "PrevYearCount", "CurrentYearCount", "PctChange") \
 .orderBy("PrimaryType", "Year")

# 6. Force-write the final single CSV partition directly into your local directory
output_path = f"file://{current_dir}/local_yoy_output"
final_yoy.coalesce(1).write.mode("overwrite").option("header", "true").csv(output_path)

spark.stop()
print(f"--- Spark analysis complete! Output saved to: {current_dir}/local_yoy_output ---")
