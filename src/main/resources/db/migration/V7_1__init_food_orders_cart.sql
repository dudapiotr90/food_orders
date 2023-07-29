CREATE TABLE cart
(
    cart_id             SERIAL      NOT NULL,
    customer_id         INT         NOT NULL,
    order_detail_id     INT         NOT NULL,
    PRIMARY KEY (cart_id),
    CONSTRAINT fk_cart_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_car_order_detail
        FOREIGN KEY (order_detail_id)
            REFERENCES order_detail (order_detail_id)
);