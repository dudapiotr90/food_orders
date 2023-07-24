CREATE TABLE bill
(
    bill_id         SERIAL                      NOT NULL,
    bill_number     VARCHAR(64)                 NOT NULL    UNIQUE,
    date_time       TIMESTAMP WITH TIME ZONE    NOT NULL,
    price           NUMERIC(7,2)                NOT NULL,
    payed           BOOLEAN                     NOT NULL,
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