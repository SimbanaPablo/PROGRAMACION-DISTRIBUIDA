package com.programacion.distribuida.authors.db;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class BookAuthorId {
    private String bookIsbn;
    private Integer authorId;
}