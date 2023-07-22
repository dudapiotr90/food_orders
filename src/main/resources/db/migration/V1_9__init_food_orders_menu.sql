CREATE TABLE menu
(
    menu_id         SERIAL      NOT NULL,
    name            VARCHAR(32) NOT NULL,
    description     TEXT,
    PRIMARY KEY (menu_id)
);