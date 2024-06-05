package com.proyecto.gestock.purchaseorder.application;

import com.proyecto.gestock.purchaseorder.domain.PurchaseOrder;
import com.proyecto.gestock.purchaseorder.domain.PurchaseOrderService;
import com.proyecto.gestock.purchaseorder.domain.Status;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

<<<<<<< HEAD
    @PreAuthorize("hasRole('ROLE_ADMIN')")
=======
    //--------ADMIN--------//
>>>>>>> d9032df2b5372ae22aca1cfdc394eb473e8c5630
    @GetMapping("/all")
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        return new ResponseEntity<>(purchaseOrderService.findAllPurchaseOrders(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(purchaseOrderService.findPurchaseOrderById(id), HttpStatus.OK);
    }

<<<<<<< HEAD
    @PreAuthorize("hasRole('ROLE_ADMIN')")
=======
    @PatchMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrderStatusById(@PathVariable Long id, @RequestParam Status status) {
        return new ResponseEntity<>(purchaseOrderService.updatePurchaseOrderStatusById(id, status), HttpStatus.OK);
    }


>>>>>>> d9032df2b5372ae22aca1cfdc394eb473e8c5630
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePurchaseOrderById(@PathVariable Long id) {
        purchaseOrderService.deletePurchaseOrderById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
