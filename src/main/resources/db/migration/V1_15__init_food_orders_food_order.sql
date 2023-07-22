CREATE TABLE food_order
(
    order_id                SERIAL                          NOT NULL,
    order_number            VARCHAR(64)                     NOT NULL    UNIQUE,
    received_date_time      TIMESTAMP WITH TIME ZONE        NOT NULL,
    completed_date_time     TIMESTAMP WITH TIME ZONE        NOT NULL,
    customer_comment        TEXT,
    realized                BOOLEAN                         NOT NULL,
    restaurant_id           INT                             NOT NULL,
    customer_id             INT                             NOT NULL,
    PRIMARY KEY (order_id),
    CONSTRAINT fk_food_order_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id),
    CONSTRAINT fk_food_order_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id)
);