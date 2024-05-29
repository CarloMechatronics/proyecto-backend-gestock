package com.proyecto.gestock.purchaseorder.infrastructure;

import com.proyecto.gestock.purchaseorder.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
