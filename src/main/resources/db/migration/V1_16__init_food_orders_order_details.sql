CREATE TABLE order_item
(
    order_item_id        SERIAL          NOT NULL,
    quantity             INT,
    food_order_id        INT             NOT NULL,
    food_id              INT             NOT NULL,
    PRIMARY KEY (order_item_id),
    CONSTRAINT fk_order_item_food_order
            FOREIGN KEY (food_order_id)
                REFERENCES food_order (order_id),
    CONSTRAINT fk_order_item_food
            FOREIGN KEY (food_id)
                REFERENCES food (food_id)
);