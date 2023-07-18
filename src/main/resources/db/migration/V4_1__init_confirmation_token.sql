CREATE TABLE confirmation_token
(
    confirmation_token_id       SERIAL                      NOT NULL,
    token                       VARCHAR(64)                 NOT NULL,
    created_at                  TIMESTAMP WITH TIME ZONE    NOT NULL,
    expires_at                  TIMESTAMP WITH TIME ZONE    NOT NULL,
    confirmed_at                TIMESTAMP WITH TIME ZONE,
    account_id                  INT                         NOT NULL,
    PRIMARY KEY (confirmation_token_id),
    CONSTRAINT fk_confirmation_token_account
        FOREIGN KEY (account_id)
            REFERENCES account (account_id)
);