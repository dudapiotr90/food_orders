ALTER TABLE food_order
DROP CONSTRAINT fk_food_order_order_detail;

ALTER TABLE food_order
RENAME COLUMN order_detail_id TO in_progress;

ALTER TABLE food_order
ALTER COLUMN in_progress TYPE BOOLEAN USING in_progress::BOOLEAN ;

