package com.proyecto.gestock.purchaseorder.application;

import com.proyecto.gestock.purchaseorder.domain.PurchaseOrder;
import com.proyecto.gestock.purchaseorder.domain.PurchaseOrderService;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase-order")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        return new ResponseEntity<>(purchaseOrderService.findAllPurchaseOrders(), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(purchaseOrderService.findPurchaseOrderById(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePurchaseOrderById(@PathVariable Long id) {
        purchaseOrderService.deletePurchaseOrderById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
