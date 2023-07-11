CREATE TABLE bill
(
    bill_id         SERIAL                      NOT NULL,
    bill_number     VARCHAR(64)                 NOT NULL    UNIQUE,
    date_type       TIMESTAMP WITH TIME ZONE    NOT NULL,
    owner_id        INT                         NOT NULL,
    customer_id     INT                         NOT NULL,
    PRIMARY KEY (bill_id),
    CONSTRAINT fk_bill_owner
        FOREIGN KEY (owner_id)
            REFERENCES owner (owner_id),
    CONSTRAINT fk_bill_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id)
);