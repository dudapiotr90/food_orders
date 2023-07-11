CREATE TABLE delivery_service
(
    delivery_service_id     SERIAL          NOT NULL,
    delivery_number         VARCHAR(128)    NOT NULL,
    price                   NUMERIC(5,2)    NOT NULL,
    status                  VARCHAR(32)     NOT NULL,
    local_id                INT             NOT NULL,
    owner_id                INT             NOT NULL,
    PRIMARY KEY (delivery_service_id),
    CONSTRAINT fk_delivery_service_local
        FOREIGN KEY (local_id)
            REFERENCES local (local_id),
    CONSTRAINT fk_delivery_service_owner
        FOREIGN KEY (owner_id)
            REFERENCES owner (local_id)
);