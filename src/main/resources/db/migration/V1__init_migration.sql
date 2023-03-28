CREATE TABLE "order"
(
    id UUID NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE product
(
    id UUID NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE order_product
(
    id         UUID NOT NULL,
    order_id   UUID NOT NULL,
    product_id UUID NOT NULL,
    CONSTRAINT pk_orderproduct PRIMARY KEY (id)
);

ALTER TABLE order_product
    ADD CONSTRAINT FK_ORDERPRODUCT_ON_ORDER FOREIGN KEY (order_id) REFERENCES "order" (id);

ALTER TABLE order_product
    ADD CONSTRAINT FK_ORDERPRODUCT_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);