UPDATE tag
SET created_date = CURRENT_DATE
WHERE created_date IS NULL;

UPDATE tag
SET last_modified_date = CURRENT_DATE
WHERE last_modified_date IS NULL;

UPDATE tag
SET last_modified_by = 'system'
WHERE last_modified_by IS NULL;

UPDATE tag
SET created_by = 'system'
WHERE created_by IS NULL;

UPDATE bookmark 
SET scrapable = true
WHERE scrapable IS NULL;

CREATE TABLE bookmark_jdbc AS
SELECT * FROM bookmark;

CREATE TABLE bookmark_tag_jdbc AS
SELECT * FROM bookmark_tag;

CREATE TABLE tag_jdbc AS
SELECT * FROM tag;



