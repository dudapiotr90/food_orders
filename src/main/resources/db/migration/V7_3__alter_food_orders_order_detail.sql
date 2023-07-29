ALTER TABLE order_detail
DROP CONSTRAINT fk_order_detail_customer;

ALTER TABLE order_detail
RENAME COLUMN customer_id TO cart_id;

ALTER TABLE order_detail
ADD CONSTRAINT fk_order_detail_cart
        FOREIGN KEY (cart_id)
            REFERENCES cart (cart_id);