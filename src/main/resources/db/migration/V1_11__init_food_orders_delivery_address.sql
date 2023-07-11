CREATE TABLE delivery_address
(
    delivery_address_id         SERIAL          NOT NULL,
    city                        VARCHAR(32)     NOT NULL,
    street                      VARCHAR(32)     NOT NULL,
    local_id                    INT             NOT NULL,
    PRIMARY KEY (delivery_address_id),
    CONSTRAINT  fk_delivery_address_local
        FOREIGN KEY (local_id)
            REFERENCES  local (local_id)
);