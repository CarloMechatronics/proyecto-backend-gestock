package com.proyecto.gestock.supplier.application;

import com.proyecto.gestock.supplier.domain.SupplierService;
import com.proyecto.gestock.supplier.dto.SupplierDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Supplier")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> supplierDTOs = supplierService.getAllSuppliers();
        return ResponseEntity.ok(supplierDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SupplierDTO> deleteSupplierById(@PathVariable Long id) {
        supplierService.deleteSupplierById(id);
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }



}
