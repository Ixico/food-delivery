ALTER TABLE "order"
    ADD COLUMN products_price NUMERIC(6, 2),
    ADD COLUMN delivery_cost  NUMERIC(6, 2),
    ALTER COLUMN products_price SET NOT NULL,
    ALTER COLUMN delivery_cost SET NOT NULL;