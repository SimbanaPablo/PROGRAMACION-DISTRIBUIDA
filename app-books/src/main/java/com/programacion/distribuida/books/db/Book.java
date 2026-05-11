package com.programacion.distribuida.books.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
    @Id
    private String isbn;

    @OneToOne(mappedBy = "book")
    private Inventory inventory;
    
    private Double price;
    private String title;
    private Integer version;
}
