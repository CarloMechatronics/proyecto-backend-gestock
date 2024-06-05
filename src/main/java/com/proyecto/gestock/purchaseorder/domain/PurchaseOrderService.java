package com.proyecto.gestock.purchaseorder.domain;

import com.proyecto.gestock.authentication.utils.Authorization;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.exceptions.UnauthorizedOperationException;
import com.proyecto.gestock.purchaseorder.infrastructure.PurchaseOrderRepository;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final Authorization authorization;

    @Autowired
    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, Authorization authorization) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.authorization = authorization;
    }

    //--------ADMIN--------//
    //----GET----//
    @Transactional(readOnly = true)
    public List<PurchaseOrder> findAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PurchaseOrder findPurchaseOrderById(Long id) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order with id "+ id + " not found"));
    }

    //----PATCH----//
    @Transactional
    public PurchaseOrder updatePurchaseOrderStatusById(Long id, Status status) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Purchase Order with id "+ id + " not found"));
        if (purchaseOrder.getStatus().equals(status))
            throw new IllegalArgumentException("Purchase Order status is already set as " + status);
        purchaseOrder.setStatus(status);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    //----DELETE----//
    @Transactional
    public void deletePurchaseOrderById(Long id) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        if (!purchaseOrderRepository.existsById(id))
            throw new ResourceNotFoundException("Purchase Order with id "+ id + " not found");

        purchaseOrderRepository.deleteById(id);
    }
}
