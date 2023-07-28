CREATE TABLE order_detail
(
    order_detail_id         SERIAL      NOT NULL,
    customer_id             INT         NOT NULL,
    order_id                INT         NOT NULL,
    PRIMARY KEY (order_detail_id),
    CONSTRAINT fk_order_detail_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_order_detail_food_order
        FOREIGN KEY (order_id)
            REFERENCES food_order (order_id)
);