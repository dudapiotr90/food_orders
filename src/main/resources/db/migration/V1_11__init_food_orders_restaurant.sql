CREATE TABLE restaurant
(
    restaurant_id        SERIAL      NOT NULL,
    name            VARCHAR(32) NOT NULL,
    description     TEXT,
    local_type      VARCHAR(32) NOT NULL,
    menu_id         INT,
    owner_id        INT         NOT NULL,
    PRIMARY KEY (restaurant_id),
    CONSTRAINT fk_restaurant_owner
        FOREIGN KEY (owner_id)
            REFERENCES owner (owner_id),
    CONSTRAINT fk_restaurant_menu
        FOREIGN KEY (menu_id)
            REFERENCES menu (menu_id)
);