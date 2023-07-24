CREATE TABLE developer
(
    developer_id    SERIAL              NOT NULL,
    name            VARCHAR(32)         NOT NULL,
    surname         VARCHAR(32)         NOT NULL,
    account_id      INT                 NOT NULL,
    PRIMARY KEY (developer_id),
    CONSTRAINT fk_developer_account
        FOREIGN KEY (account_id)
            REFERENCES account (account_id)
);