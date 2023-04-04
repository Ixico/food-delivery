CREATE TABLE "user"
(
    id         UUID NOT NULL,
    email      TEXT NOT NULL,
    password   TEXT NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);
