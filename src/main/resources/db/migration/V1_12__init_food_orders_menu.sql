CREATE TABLE menu
(
    menu_id         SERIAL      NOT NULL,
    name            VARCHAR(32) NOT NULL,
    description     TEXT,
    local_id        INT         NOT NULL,
    PRIMARY KEY (menu_id),
    CONSTRAINT  fk_menu_local
        FOREIGN KEY (local_id)
            REFERENCES local (local_id)
);