ALTER TABLE account
DROP CONSTRAINT fk_account_address;
ALTER TABLE account
ADD CONSTRAINT fk_account_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id);