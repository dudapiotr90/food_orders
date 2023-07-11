CREATE TABLE food_order
(
    order_id                SERIAL                          NOT NULL,
    order_number            VARCHAR(64)                     NOT NULL    UNIQUE,
    received_date_time      TIMESTAMP WITH TIME ZONE        NOT NULL,
    completed_date_time     TIMESTAMP WITH TIME ZONE        NOT NULL,
    customer_comment        TEXT,
    local_id                INT                             NOT NULL,
    customer_id             INT                             NOT NULL,
    PRIMARY KEY (order_id),
    CONSTRAINT fk_order_local
        FOREIGN KEY (local_id)
            REFERENCES local (local_id),
    CONSTRAINT fk_order_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id)
);