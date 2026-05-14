CREATE TABLE books
(
    isbn    VARCHAR(255) NOT NULL,
    price   DECIMAL,
    title   VARCHAR(255),
    version INTEGER,
    CONSTRAINT pk_books PRIMARY KEY (isbn)
);

CREATE TABLE inventory
(
    book_isbn VARCHAR(255) NOT NULL,
    sold      INTEGER,
    supplied  INTEGER,
    version   INTEGER,
    CONSTRAINT pk_inventory PRIMARY KEY (book_isbn)
);

ALTER TABLE inventory
    ADD CONSTRAINT FK_INVENTORY_ON_BOOK_ISBN FOREIGN KEY (book_isbn) REFERENCES books (isbn);