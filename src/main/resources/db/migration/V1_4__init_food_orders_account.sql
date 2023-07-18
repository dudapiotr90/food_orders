CREATE TABLE account
(
    account_id      SERIAL                      NOT NULL,
    login           VARCHAR(32)                 NOT NULL    UNIQUE,
    password        VARCHAR(128)                NOT NULL,
    email           VARCHAR(128)                NOT NULL    UNIQUE,
    phone           VARCHAR(128)                NOT NULL,
    creation_date   TIMESTAMP WITH TIME ZONE    NOT NULL,
    status          BOOLEAN                     NOT NULL,
    locked          BOOLEAN                     NOT NULL,
    enabled         BOOLEAN                     NOT NULL,
    address_id      INT,
    api_role_id     INT,
    PRIMARY KEY (account_id),
    CONSTRAINT fk_account_address
        FOREIGN KEY (account_id)
            REFERENCES account (account_id),
    CONSTRAINT fk_account_api_role
        FOREIGN KEY (api_role_id)
            REFERENCES api_role (api_role_id)
);