CREATE TABLE books_authors
(
    bookIsbn   VARCHAR(255) NOT NULL,
    authors_id INTEGER      NOT NULL,
    CONSTRAINT pk_books_authors PRIMARY KEY (bookIsbn, authors_id)
);

ALTER TABLE books_authors
    ADD CONSTRAINT FK_BOOKS_AUTHORS_ON_AUTHORS FOREIGN KEY (authors_id) REFERENCES authors (id);