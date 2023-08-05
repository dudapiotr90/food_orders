ALTER TABLE food_order
    ALTER COLUMN completed_date_time DROP NOT NULL;

ALTER TABLE food_order
    ADD COLUMN cancel_till TIMESTAMP WITH TIME ZONE     NOT NULL;

ALTER TABLE food_order
    ADD COLUMN canceled BOOLEAN NOT NULL;