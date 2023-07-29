ALTER TABLE customer
ADD COLUMN cart_id INT,
ADD CONSTRAINT fk_customer_cart
        FOREIGN KEY (cart_id)
            REFERENCES cart (cart_id);