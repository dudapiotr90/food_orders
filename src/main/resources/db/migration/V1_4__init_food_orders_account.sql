CREATE TABLE account
(
    account_id      SERIAL                      NOT NULL,
    login           VARCHAR(32)                 NOT NULL    UNIQUE,
    password        VARCHAR(128)                NOT NULL    UNIQUE,
    email           VARCHAR(128)                NOT NULL    UNIQUE,
    phone           VARCHAR(128)                NOT NULL    UNIQUE,
    creation_date   TIMESTAMP WITH TIME ZONE    NOT NULL,
    address_id      INT                         NOT NULL,
    PRIMARY KEY (account_id),
    CONSTRAINT fk_customer_account
        FOREIGN KEY (account_id)
            REFERENCES account (account_id)
);