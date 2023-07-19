CREATE TABLE local
(
    local_id        SERIAL      NOT NULL,
    name            VARCHAR(32) NOT NULL,
    description     TEXT,
    local_type_id   INT         NOT NULL,
    owner_id        INT         NOT NULL,
    PRIMARY KEY (local_id),
    CONSTRAINT fk_local_local_type
        FOREIGN KEY (local_type_id)
            REFERENCES local_type (local_type_id),
    CONSTRAINT fk_local_owner
        FOREIGN KEY (owner_id)
            REFERENCES owner (owner_id)
);