CREATE TABLE address
(
    address_id      SERIAL          NOT NULL,
    city            VARCHAR(32)     NOT NULL,
    postal_code     VARCHAR(32)     NOT NULL,
    address         TEXT            NOT NULL,
    PRIMARY KEY (address_id)
);

