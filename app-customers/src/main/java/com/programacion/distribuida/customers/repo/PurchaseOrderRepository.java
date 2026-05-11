package com.programacion.distribuida.customers.repo;

import com.programacion.distribuida.customers.db.PurcharseOrder;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class PurchaseOrderRepository implements PanacheRepositoryBase<PurcharseOrder, Integer> {
}
