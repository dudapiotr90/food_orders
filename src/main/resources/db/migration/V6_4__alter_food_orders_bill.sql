ALTER TABLE bill
RENAME COLUMN price TO amount;

ALTER TABLE bill
ADD COLUMN order_id INT,
ADD CONSTRAINT fk_bill_order
        FOREIGN KEY (order_id)
            REFERENCES food_order (order_id);