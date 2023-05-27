ALTER TABLE "user"
    ADD COLUMN locked BOOLEAN,
    ALTER COLUMN locked SET NOT NULL,
    ADD COLUMN login_attempts INTEGER,
    ALTER COLUMN login_attempts SET NOT NULL,
    ADD COLUMN lock_timestamp TIMESTAMP;