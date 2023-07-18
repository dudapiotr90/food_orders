INSERT INTO account (login, password, email, phone, creation_date,status,unlocked,enabled,api_role_id)
VALUES
(
'admin',
'$2a$12$ogbeKsL0IFpAv7KV.n0zqOrMmsMg0kZU7Soc1Dak0ycc8WBkwvk.e',
'admin@mail.com',
'+48 000 000 000',
CURRENT_TIMESTAMP,
TRUE,
FALSE,
TRUE,
1
);
INSERT INTO account_manager (api_role_id,account_id)
VALUES  (1,1);
