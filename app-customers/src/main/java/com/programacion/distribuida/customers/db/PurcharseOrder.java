package com.programacion.distribuida.customers.db;

import com.aayushatharva.brotli4j.common.annotations.Local;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "purcharse_orders")
@Setter
@Getter
public class PurcharseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDateTime deliveredor;
    private LocalDateTime placedon;
    private Integer status;
    private Integer total;
}
