CREATE TABLE food_image
(
    food_image_id       SERIAL          NOT NULL,
    file_path           VARCHAR(128)    NOT NULL,
    food_id             INT             NOT NULL,
    PRIMARY KEY (food_image_id),
    CONSTRAINT fk_food_image_food
        FOREIGN (food_id)
            REFERENCES food (food_id)
);

