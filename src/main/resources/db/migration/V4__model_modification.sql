CREATE TABLE address
(
    id     UUID NOT NULL,
    city   TEXT NOT NULL,
    street TEXT NOT NULL,
    number TEXT NOT NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

ALTER TABLE "order"
    ADD COLUMN creation_date TIMESTAMP,
    ADD COLUMN total_price   NUMERIC(6, 2),
    ADD COLUMN user_id       UUID,
    ADD COLUMN address_id    UUID,
    ALTER COLUMN creation_date SET NOT NULL,
    ALTER COLUMN total_price SET NOT NULL,
    ALTER COLUMN user_id SET NOT NULL,
    ALTER COLUMN address_id SET NOT NULL,
    ADD CONSTRAINT FK_ORDER_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id),
    ADD CONSTRAINT FK_ORDER_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE order_product
    ADD COLUMN quantity INTEGER,
    ALTER COLUMN quantity SET NOT NULL;

ALTER TABLE product
    ADD COLUMN name  TEXT,
    ADD COLUMN price NUMERIC(6, 2),
    ALTER COLUMN name SET NOT NULL,
    ALTER COLUMN price SET NOT NULL;

ALTER TABLE "user"
    ADD COLUMN address_id UUID,
    ALTER COLUMN address_id SET NOT NULL,
    ADD CONSTRAINT FK_USER_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);
