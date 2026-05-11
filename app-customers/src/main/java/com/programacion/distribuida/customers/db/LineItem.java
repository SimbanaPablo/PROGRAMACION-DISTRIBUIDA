package com.programacion.distribuida.customers.db;

import jakarta.persistence.*;

@Entity
@Table(name = "line-items")
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private PurcharseOrder purcharseOrder;

    private Integer quantity;
    private String isbn;

}
