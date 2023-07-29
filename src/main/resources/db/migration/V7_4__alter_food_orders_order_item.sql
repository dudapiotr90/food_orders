ALTER TABLE order_item
DROP CONSTRAINT fk_order_item_order_detail;

ALTER TABLE order_item
RENAME COLUMN order_detail_id TO cart_id;

ALTER TABLE order_item
ADD CONSTRAINT fk_order_item_cart
        FOREIGN KEY (cart_id)
            REFERENCES cart (cart_id);