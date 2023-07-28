ALTER TABLE order_item
DROP CONSTRAINT fk_order_item_food_order;

ALTER TABLE order_item
RENAME COLUMN food_order_id TO order_detail_id;

ALTER TABLE order_item
ADD CONSTRAINT fk_order_item_order_detail
        FOREIGN KEY (order_detail_id)
            REFERENCES order_detail (order_detail_id);