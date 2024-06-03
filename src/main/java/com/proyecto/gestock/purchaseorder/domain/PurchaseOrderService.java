package com.proyecto.gestock.purchaseorder.domain;

import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.purchaseorder.infrastructure.PurchaseOrderRepository;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    //--------ADMIN--------//
    //----GET----//
    public List<PurchaseOrder> findAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder findPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order with id "+ id + " not found"));
    }

    //----DELETE----//
    public void deletePurchaseOrderById(Long id) {
        if (!purchaseOrderRepository.existsById(id))
            throw new ResourceNotFoundException("Purchase Order with id "+ id + " not found");

        purchaseOrderRepository.deleteById(id);
    }
}
