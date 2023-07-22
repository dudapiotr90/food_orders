CREATE TABLE delivery
(
    delivery_id     SERIAL          NOT NULL,
    delivery_number         VARCHAR(128)    NOT NULL UNIQUE,
    price                   NUMERIC(5,2)    NOT NULL,
    delivered               BOOLEAN         NOT NULL,
    restaurant_id                INT             NOT NULL,
    owner_id                INT             NOT NULL,
    PRIMARY KEY (delivery_id),
    CONSTRAINT fk_delivery_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id),
    CONSTRAINT fk_delivery_owner
        FOREIGN KEY (owner_id)
            REFERENCES owner (owner_id)
);
-- TODO check with ERD diagram