CREATE TABLE activation_token
(
    id         UUID      NOT NULL,
    user_id    UUID      NOT NULL,
    token      TEXT      NOT NULL,
    expiration TIMESTAMP NOT NULL,
    CONSTRAINT pk_activationtoken PRIMARY KEY (id)
);

ALTER TABLE activation_token
    ADD CONSTRAINT FK_ACTIVATIONTOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE "user"
    ADD COLUMN enabled BOOLEAN,
    ALTER COLUMN enabled SET NOT NULL;