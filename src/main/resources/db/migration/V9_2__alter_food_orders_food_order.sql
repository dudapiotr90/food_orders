ALTER TABLE food_order
    ADD COLUMN customer_id INT NOT NULL;

ALTER TABLE food_order
    ADD CONSTRAINT fk_food_order_customer
            FOREIGN KEY (customer_id)
                REFERENCES customer (customer_id);