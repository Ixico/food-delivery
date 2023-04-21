ALTER TABLE activation_token
    ADD COLUMN activation_attempts INTEGER,
    ALTER COLUMN activation_attempts SET NOT NULL;
