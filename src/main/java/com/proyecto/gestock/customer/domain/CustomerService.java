package com.proyecto.gestock.customer.domain;

import com.proyecto.gestock.authentication.utils.Authorization;
import com.proyecto.gestock.customer.dto.CustomerCreateDto;
import com.proyecto.gestock.customer.dto.CustomerResponseDto;
import com.proyecto.gestock.customer.dto.CustomerUpdateDto;
import com.proyecto.gestock.customer.infrastructure.CustomerRepository;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.exceptions.UnauthorizedOperationException;
import com.proyecto.gestock.orderitem.domain.OrderItem;
import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.infrastructure.ProductRepository;
import com.proyecto.gestock.purchaseorder.infrastructure.PurchaseOrderRepository;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import com.proyecto.gestock.shoppingcart.infrastructure.ShoppingCartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService{
    private final CustomerRepository customerRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ModelMapper nonNullMapper;
    private final Authorization authorization;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ProductRepository productRepository, PurchaseOrderRepository purchaseOrderRepository , ModelMapper modelMapper, ModelMapper nonNullMapper, ShoppingCartRepository shoppingCartRepository, Authorization authorization) {
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.nonNullMapper = nonNullMapper;
        this.authorization = authorization;
    }

    //--------ADMIN--------//
    //----GET----//
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long id) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));
    }

    public Customer findCustomerByName(String name) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return customerRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with name " + name + " not found"));
    }

    public Customer findCustomerByEmail(String email) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with email " + email + " not found"));
    }

    //----POST----//
    public Customer saveCustomer(CustomerCreateDto customerCreateDto) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return customerRepository.save(modelMapper.map(customerCreateDto, Customer.class));
    }

    //----DELETE----//
    public void deleteCustomerById(Long id) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        if (!customerRepository.existsById(id))
            throw new ResourceNotFoundException("Customer with id " + id + " not found");

        customerRepository.deleteById(id);
    }


    //--------ANYONE--------//
    public CustomerResponseDto registerCustomer(CustomerCreateDto customerCreateDto) {
        Customer customer = customerRepository.save(modelMapper.map(customerCreateDto, Customer.class));

        return modelMapper.map(customer, CustomerResponseDto.class);
    }


    //--------CUSTOMER--------//
    //----GET----//
    public ShoppingCart findShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart with id " + id + " not foun"));
    }

    public BigDecimal findTotalAmountById(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart with " + id + " not found"));

        return shoppingCart.getOrderItems().stream()
                .map(orderItem -> orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //----POST----//
    public ShoppingCart createShoppingCartByIds(Long customerId, List<Long> productIds) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        List<Product> products = productRepository.findAllById(productIds);

        ShoppingCart shoppingCart = new ShoppingCart();

        List<OrderItem> orderItems = products.stream().map(product -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(1);
            orderItem.setShoppingCart(shoppingCart);
            return orderItem;
        }).collect(Collectors.toList());

        shoppingCart.setOrderItems(orderItems);

        return shoppingCartRepository.save(shoppingCart);
    }

    //----PATCH----//
    public CustomerResponseDto updateCustomerById(Long id, CustomerUpdateDto customerUpdateDto) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));

        nonNullMapper.map(customerUpdateDto, existing);

        customerRepository.save(existing);

        return modelMapper.map(existing, CustomerResponseDto.class);
    }


    public ShoppingCart updateShoppingCartByIds(Long id, List<Long> productIds) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart with id " + id + " not found"));
        List<Product> products = productRepository.findAllById(productIds);

        List<OrderItem> orderItems = products.stream().map(product -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(1);
            orderItem.setShoppingCart(shoppingCart);
            return orderItem;
        }).collect(Collectors.toList());

        shoppingCart.setOrderItems(orderItems);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart updateShoppingCartItemQuantityByIds(Long cartId, Long productId, Integer quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart with id " + cartId + " not found"));

        Optional<OrderItem> orderItemOptional = shoppingCart.getOrderItems().stream()
                .filter(orderItem -> orderItem.getProduct().getId().equals(productId))
                .findFirst();
        if (orderItemOptional.isPresent()) {
            OrderItem orderItem = orderItemOptional.get();
            orderItem.setQuantity(quantity);
            shoppingCartRepository.save(shoppingCart);
        } else
            throw new ResourceNotFoundException("Product not found in purchase order");

        return shoppingCart;
    }

    //----DELETE----//
    public void deleteShoppingCartById(Long id) {
        if (!shoppingCartRepository.existsById(id))
            throw new ResourceNotFoundException("Shopping Cart with id " + id + " not found");

        shoppingCartRepository.deleteById(id);
    }

    @Bean(name = "CustomerDetailService")
    public UserDetailsService userDetailsService(){
        return name -> {
            Customer customer = customerRepository
                    .findByEmail(name)
                    .orElseThrow(() ->new UsernameNotFoundException("Customer not found"));
            return (UserDetails) customer;
        };
    }

}