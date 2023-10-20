-- DROP Table IF EXISTS product;

CREATE TABLE popular_product (
                        id              serial PRIMARY KEY,
                        name            TEXT NOT NULL,
                        shows_count     int NOT NULL DEFAULT(0)
);