INSERT INTO account (login, password, email, phone, creation_date,status,locked,enabled)
VALUES
(
'admin',
'$2a$12$ogbeKsL0IFpAv7KV.n0zqOrMmsMg0kZU7Soc1Dak0ycc8WBkwvk.e',
'admin@mail.com',
'+48 000 000 000',
CURRENT_TIMESTAMP,
TRUE,
FALSE,
TRUE
)
