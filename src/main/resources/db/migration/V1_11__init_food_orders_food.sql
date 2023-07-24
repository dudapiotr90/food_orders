CREATE TABLE food
(
    food_id         SERIAL          NOT NULL,
    name            VARCHAR(64)     NOT NULL,
    description     TEXT            NOT NULL,
    price           NUMERIC(6,2)    NOT NULL,
    food_type       VARCHAR(64)     NOT NULL,
    menu_id         INT             NOT NULL,
    PRIMARY KEY (food_id),
    CONSTRAINT  fk_food_menu
        FOREIGN KEY (menu_id)
            REFERENCES  menu (menu_id)
);