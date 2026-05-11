CREATE TABLE books
(
    isbn    VARCHAR(255) NOT NULL,
    price   DOUBLE PRECISION,
    title   VARCHAR(255),
    version INTEGER,
    CONSTRAINT pk_books PRIMARY KEY (isbn)
);

CREATE TABLE inventory
(
    isbn     VARCHAR(255) NOT NULL,
    sold     INTEGER,
    supplied INTEGER,
    version  INTEGER,
    CONSTRAINT pk_inventory PRIMARY KEY (isbn)
);

ALTER TABLE inventory
    ADD CONSTRAINT FK_INVENTORY_ON_ISBN FOREIGN KEY (isbn) REFERENCES books (isbn);