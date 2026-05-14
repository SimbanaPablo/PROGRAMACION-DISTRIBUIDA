package com.programacion.distribuida.books.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.programacion.distribuida.books.db.Book}
 */
@Value
public class BookDto2 implements Serializable {
    String isbn;
    Integer inventorySold;
    Integer inventorySupplied;
    Integer inventoryVersion;
    BigDecimal price;
    String title;
    Integer version;
}