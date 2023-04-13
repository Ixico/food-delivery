ALTER TABLE "user"
    ADD COLUMN name         TEXT,
    ADD COLUMN surname      TEXT,
    ADD COLUMN phone_number TEXT,
    ALTER COLUMN name SET NOT NULL,
    ALTER COLUMN surname SET NOT NULL,
    ALTER COLUMN phone_number SET NOT NULL;