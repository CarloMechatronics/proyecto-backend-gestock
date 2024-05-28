package com.proyecto.gestock.supplier.infrastructure;

import com.proyecto.gestock.supplier.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
