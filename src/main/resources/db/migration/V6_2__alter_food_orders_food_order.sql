ALTER TABLE food_order
DROP CONSTRAINT fk_food_order_customer;

ALTER TABLE food_order
RENAME COLUMN customer_id TO order_detail_id;

ALTER TABLE food_order
ADD CONSTRAINT fk_food_order_order_detail
        FOREIGN KEY (order_detail_id)
            REFERENCES order_detail (order_detail_id);