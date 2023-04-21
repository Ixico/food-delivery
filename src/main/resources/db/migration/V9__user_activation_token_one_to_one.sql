ALTER TABLE "activation_token"
    ADD CONSTRAINT uc_activationtoken_userid UNIQUE (user_id)