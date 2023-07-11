CREATE TABLE owner
(
    owner_id        SERIAL              NOT NULL,
    name            VARCHAR(32)         NOT NULL,
    surname         VARCHAR(32)         NOT NULL,
    account_id      VARCHAR(32)         NOT NULL,
    PRIMARY KEY (customer_id),
    CONSTRAINT fk_owner_account
        FOREIGN KEY (account_id)
            REFERENCES account (account_id)
);