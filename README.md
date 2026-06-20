# Chicago Crime Data Analytics using Hadoop, Pig, and Spark

## Project Overview

This project performs large-scale crime data analysis using Big Data technologies including Apache Hadoop MapReduce, Apache Pig, and Apache Spark.

The objective is to preprocess crime data, perform distributed analysis, identify crime patterns, and compute Year-over-Year (YoY) crime trends.

## Technologies Used

* Apache Hadoop MapReduce
* Apache Pig
* Apache Spark (PySpark)
* Java
* Python
* CSV Dataset

## Project Architecture

Raw Crime Dataset
↓
Apache Pig (Data Preprocessing)
↓
Cleaned Dataset
↓
Hadoop MapReduce Analytics
↓
Apache Spark Advanced Analytics
↓
Result Generation & Visualization

## Dataset Attributes

The dataset contains:

* ID
* Date
* Block
* Primary Type
* Location Description
* Arrest Status
* Domestic Status
* District
* Community Area
* Year
* Latitude
* Longitude

## Data Preprocessing (Apache Pig)

File:

* test_preprocessing.pig

Tasks:

* Load raw CSV data
* Remove invalid records
* Remove header rows
* Validate year values
* Generate Spark-compatible schema
* Store cleaned output

## Hadoop MapReduce Analysis

### 1. Crime Volume by Year

Files:

* YearlyDriver.java
* YearlyMapper.java
* YearlyReducer.java

Output:

* Total crimes reported per year

### 2. Top Crime Categories

Files:

* TopCrimeDriver.java
* TopCrimeMapper.java
* TopCrimeReducer.java

Output:

* Most frequently occurring crime types

### 3. Top Crime Locations

Files:

* TopLocationDriver.java
* TopLocationMapper.java
* TopLocationReducer.java

Output:

* Locations with highest crime frequency

### 4. Arrest vs Non-Arrest Analysis

Files:

* ArrestDriver.java
* ArrestMapper.java
* ArrestReducer.java

Output:

* Distribution of arrest and non-arrest incidents

## Apache Spark Analysis

### Year-over-Year Crime Change Analysis

File:

* yoy_crime_change.py

Features:

* Crime count aggregation
* Window functions
* Previous year comparison
* Percentage change calculation
* YoY trend analysis

Formula:

Percentage Change =
((Current Year Count - Previous Year Count)
/ Previous Year Count) × 100

## Key Insights Generated

* Crime trends over multiple years
* Most common crime categories
* Crime hotspot locations
* Arrest effectiveness analysis
* Growth and decline of crime categories

## Project Contributors

* Kunal Maheshwari
* Koushiki Ghosh
* Tsering Wangmu
* Swetha Mahapatro

## Academic Purpose

This project was developed as part of an academic Big Data Analytics study to demonstrate the practical implementation of Hadoop ecosystem technologies for large-scale data processing and analysis.

## Future Enhancements

* Hive-based querying
* Real-time streaming analytics
* Interactive dashboards using Power BI
* Machine Learning-based crime prediction

## License

Academic and Educational Use Only.
