package com.proyecto.gestock.customer.domain;

import com.proyecto.gestock.customer.dto.CustomerDTO;
import com.proyecto.gestock.customer.infrastructure.CustomerRepository;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.orderitem.domain.OrderItem;
import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.infrastructure.ProductRepository;
import com.proyecto.gestock.purchaseorder.domain.PurchaseOrder;
import com.proyecto.gestock.purchaseorder.infrastructure.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository purchaseOrderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,ProductRepository productRepository, OrderRepository purchaseOrderRepository ,ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.modelMapper = modelMapper;
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer = customerRepository.save(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }

//    public PurchaseOrder createPurchaseOrder(Long customerId, List<Long> productIds) {
//        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
//        List<Product> products = productRepository.findAllById(productIds);
//
//        PurchaseOrder purchaseOrder = new PurchaseOrder();
//        purchaseOrder.se(customer);
//
//        List<OrderItem> orderItems = products.stream().map(product -> {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setProduct(product);
//            orderItem.setPrice(product.getPrice());
//            orderItem.setQuantity(1); // Default quantity
//            orderItem.setPurchaseOrder(purchaseOrder);
//            return orderItem;
//        }).collect(Collectors.toList());
//
//        purchaseOrder.setOrderItems(orderItems);
//
//        return purchaseOrderRepository.save(purchaseOrder);
//    }

    public PurchaseOrder getPurchaseOrder(Long id) {
        return purchaseOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Purchase order not found"));
    }

//    public PurchaseOrder updatePurchaseOrder(Long id, List<Long> productIds) {
//        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Purchase order not found"));
//        List<Product> products = productRepository.findAllById(productIds);
//
//        List<OrderItem> orderItems = products.stream().map(product -> {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setProduct(product);
//            orderItem.setPrice(product.getPrice());
//            orderItem.setQuantity(1);
//            orderItem.setPurchaseOrder(purchaseOrder);
//            return orderItem;
//        }).collect(Collectors.toList());
//
//        purchaseOrder.setOrderItems(orderItems);
//        return purchaseOrderRepository.save(purchaseOrder);
//    }

//    public BigDecimal getTotalPrice(Long purchaseOrderId) {
//        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).orElseThrow(() -> new ResourceNotFoundException("Purchase order not found"));
//
//        return purchaseOrder.getOrderItems().stream()
//                .map(orderItem -> orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }


//    public PurchaseOrder updateOrderItemQuantity(Long orderId, Long productId, Integer quantity) {
//        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Purchase order not found"));
//
//        Optional<OrderItem> orderItemOptional = purchaseOrder.getOrderItems().stream()
//                .filter(orderItem -> orderItem.getProduct().getId().equals(productId))
//                .findFirst();
//        if (orderItemOptional.isPresent()) {
//            OrderItem orderItem = orderItemOptional.get();
//            orderItem.setQuantity(quantity);
//            purchaseOrderRepository.save(purchaseOrder);
//        } else {
//            throw new ResourceNotFoundException("Product not found in purchase order");
//        }
//        return purchaseOrder;
//    }

    public void deletePurchaseOrder(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Purchase order not found"));
        purchaseOrderRepository.delete(purchaseOrder);
    }
}


/*
 * C R U D
 * crear una lista de productos para comprar
 * obtener productos disponibles
 * obtener lista creada
 * actualizar lista de productos a comprar --update
 * obtener precio de productos
 * eliminar producto de la lista -- Update
 * eliminar lista de productos
 *
 */
