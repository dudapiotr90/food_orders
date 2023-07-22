CREATE TABLE delivery_address
(
    delivery_address_id         SERIAL          NOT NULL,
    city                        VARCHAR(32)     NOT NULL,
    street                      VARCHAR(32)     NOT NULL,
    restaurant_id                    INT             NOT NULL,
    PRIMARY KEY (delivery_address_id),
    CONSTRAINT  fk_delivery_address_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES  restaurant (restaurant_id)
);