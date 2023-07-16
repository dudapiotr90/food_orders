CREATE TABLE account_manager
(
    account_manager_id SERIAL      NOT NULL,
    api_role_id        INT         NOT NULL,
    account_id         INT         NOT NULL,
    PRIMARY KEY (account_manager_id),
    CONSTRAINT  fk_account_manager_api_role
        FOREIGN KEY (api_role_id)
            REFERENCES  api_role (api_role_id),
    CONSTRAINT fk_account_manager_account
        FOREIGN KEY (account_id)
            REFERENCES  account (account_id)
);