package com.proyecto.gestock.supplier.domain;

import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.supplier.dto.SupplierDTO;
import com.proyecto.gestock.supplier.infrastructure.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    public SupplierDTO getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn´t found"));
        return modelMapper.map(supplier, SupplierDTO.class);
    }

    public List<SupplierDTO> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        if (suppliers.isEmpty()) {
            return new ArrayList<>();
        }
        List<SupplierDTO> dtos = suppliers.stream()
                .map(supplier -> modelMapper.map(supplier, SupplierDTO.class))
                .collect(Collectors.toList());
        return dtos;
    }

    public void deleteSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn't found"));
        supplierRepository.deleteById(id);
    }
}
