ALTER TABLE order_item
ADD COLUMN order_id INT NOT NULL,
ADD CONSTRAINT fk_order_item_food_order
        FOREIGN KEY (order_id)
            REFERENCES food_order (order_id);