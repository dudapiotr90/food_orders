CREATE TABLE food
(
    food_id         SERIAL          NOT NULL,
    name            VARCHAR(128)    NOT NULL,
    description     TEXT            NOT NULL,
    price           NUMERIC(6,2)    NOT NULL,
    food_type_id    INT             NOT NULL,
    menu_id         INT             NOT NULL,
    PRIMARY KEY (food_id),
    CONSTRAINT  fk_food_food_type_id
        FOREIGN KEY (food_type_id)
            REFERENCES  food_type (food_type_id),
    CONSTRAINT  fk_food_menu
        FOREIGN KEY (menu_id)
            REFERENCES  menu (menu_id)
);