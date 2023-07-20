CREATE TABLE delivery
(
    delivery_id     SERIAL          NOT NULL,
    delivery_number         VARCHAR(128)    NOT NULL UNIQUE,
    price                   NUMERIC(5,2)    NOT NULL,
    status                  VARCHAR(32)     NOT NULL,
    local_id                INT             NOT NULL,
    owner_id                INT             NOT NULL,
    PRIMARY KEY (delivery_id),
    CONSTRAINT fk_delivery_local
        FOREIGN KEY (local_id)
            REFERENCES local (local_id),
    CONSTRAINT fk_delivery_owner
        FOREIGN KEY (owner_id)
            REFERENCES owner (owner_id)
);