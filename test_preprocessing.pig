-- 1. Load the raw local test file
raw_data = LOAD 'local_test_data.csv' USING PigStorage(',') AS (
    id:chararray,
    date:chararray,
    block:chararray,
    primary_type:chararray,
    location_desc:chararray,
    arrest:chararray,
    domestic:chararray,
    district:chararray,
    community_area:chararray,
    year:chararray,
    latitude:chararray,
    longitude:chararray
);

-- 2. Basic cleaning filter (removes CSV header text and broken years)
cleaned_data = FILTER raw_data BY (id != 'ID' AND year MATCHES '\\d{4}');

-- 3. Match Spark's exact full-schema layout row for row
spark_matching_schema = FOREACH cleaned_data GENERATE 
    id, 
    date, 
    block, 
    primary_type, 
    location_desc, 
    arrest, 
    domestic, 
    district, 
    community_area, 
    year, 
    latitude, 
    longitude;

-- 4. Save to your local pig output directory
STORE spark_matching_schema INTO 'local_pig_preprocessed' USING PigStorage(',');
