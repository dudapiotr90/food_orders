-- addresses

INSERT INTO address (address_id, city, postal_code, street, residence_number) VALUES
(1000, 'Kraków' , '30-001', 'Krakowska', 1),
(1001, 'Warszawa' , '00-001', 'Poznańska', 1),
(1002, 'Poznań' , '60-001', 'Warszawska', 1);

-- accounts

INSERT INTO account (account_id, login, password, email, phone, creation_date, status, unlocked, enabled, address_id,
api_role_id)
VALUES
(1000, 'customer', '$2a$12$IazEzzzOJ78XP72ooiEeSOFaPwvBvXWFRzvut9Sayy04rzGxoqgH2', 'customer@fakemail.com','+11 111 111
111', '2023-08-22 12:00:00+00:00', true, true, true, 1000, 2),
(1001, 'owner', '$2a$12$hhFoIyXgJ8y4mPPy4BZwf.4iSuIB.44r6CDTAfUOj41mAd8Q.uQcG', 'owner@fakemail.com', '+22 222 222 222',
'2023-08-22 12:00:00+00:00', true, true, true, 1000, 3),
(1002, 'developer', '$2a$12$IazEzzzOJ78XP72ooiEeSOFaPwvBvXWFRzvut9Sayy04rzGxoqgH2', 'developer@fakemail.com', '+33 333 333 333',
'2023-08-22 12:00:00+00:00', true, true, true, 1000, 4);

INSERT INTO customer(customer_id, name, surname, account_id)
VALUES(1000, 'Piotr','Klient', 1000);

INSERT INTO owner(owner_id, name, surname, account_id)
VALUES(1001, 'Piotr','Właściciel', 1001);

INSERT INTO developer(developer_id, name, surname, account_id)
VALUES(1002, 'Piotr','Programista', 1002);

-- roles assigned
INSERT INTO account_manager (api_role_id,account_id) VALUES
(2,1000),
(3,1001),
(4,1002);






