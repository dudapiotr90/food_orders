ALTER TABLE address
ADD COLUMN residence_number VARCHAR(16),
ALTER COLUMN address TYPE VARCHAR(128);

ALTER TABLE address
RENAME COLUMN address TO street;