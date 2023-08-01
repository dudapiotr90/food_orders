ALTER TABLE cart
DROP CONSTRAINT fk_car_order_detail;
ALTER TABLE cart
DROP COLUMN order_detail_id;