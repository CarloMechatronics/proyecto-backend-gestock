package com.proyecto.gestock.customer.domain;

import com.proyecto.gestock.authentication.dto.JwtAuthenticationResponseDTO;
import com.proyecto.gestock.authentication.dto.LogInDTO;
import com.proyecto.gestock.authentication.dto.SignInDTO;
import com.proyecto.gestock.authentication.utils.Authorization;
import com.proyecto.gestock.configuration.JwtService;
import com.proyecto.gestock.customer.dto.CustomerCreateDto;
import com.proyecto.gestock.customer.dto.CustomerResponseDto;
import com.proyecto.gestock.customer.dto.CustomerUpdateDto;
import com.proyecto.gestock.customer.infrastructure.CustomerRepository;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.exceptions.UnauthorizedOperationException;
import com.proyecto.gestock.orderitem.domain.OrderItem;
import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.infrastructure.ProductRepository;
import com.proyecto.gestock.purchaseorder.domain.PurchaseOrder;
import com.proyecto.gestock.purchaseorder.infrastructure.PurchaseOrderRepository;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import com.proyecto.gestock.shoppingcart.infrastructure.ShoppingCartRepository;
import com.proyecto.gestock.useraccount.domain.UserAccount;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements UserDetailsService{
    private final CustomerRepository customerRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final ModelMapper nonNullMapper;
    private final Authorization authorization;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(Authorization authorization, CustomerRepository customerRepository, ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, ModelMapper modelMapper, ModelMapper nonNullMapper, PurchaseOrderRepository purchaseOrderRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.jwtService = jwtService;
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.nonNullMapper = nonNullMapper;
        this.authorization = authorization;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new User(customer.getEmail(), customer.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public UserDetails loadUserByEmail(String email) {
        return loadUserByUsername(email);
    }

    public JwtAuthenticationResponseDTO login(LogInDTO logInDTO) {
        Customer customer = customerRepository.findByEmail(logInDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        if (!passwordEncoder.matches(logInDTO.getPassword(), customer.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        UserDetails userDetails = new User(customer.getEmail(), customer.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        JwtAuthenticationResponseDTO responseDTO = new JwtAuthenticationResponseDTO();
        responseDTO.setToken(jwtService.generateToken(userDetails));
        return responseDTO;
    }

    public JwtAuthenticationResponseDTO signIn(SignInDTO signInDTO) {
        if (customerRepository.findByEmail(signInDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        Customer customer = new Customer();
        customer.setEmail(signInDTO.getEmail());
        customer.setPassword(passwordEncoder.encode(signInDTO.getPassword()));
        customer.setName(signInDTO.getEmail());
        customer.setRegistrationDate(signInDTO.getRegistrationDate());
        customerRepository.save(customer);

        UserDetails userDetails = new User(customer.getEmail(), customer.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        JwtAuthenticationResponseDTO responseDTO = new JwtAuthenticationResponseDTO();
        responseDTO.setToken(jwtService.generateToken(userDetails));
        return responseDTO;
    }



    //--------ADMIN--------//
    //----GET----//
    @Transactional(readOnly = true)
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer findCustomerById(Long id) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByName(String name) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return customerRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with name " + name + " not found"));
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByEmail(String email) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with email " + email + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Customer> findAllCustomersByNameContains(String namePart) {
        return customerRepository.findAllByNameContains(namePart);
    }

    @Transactional(readOnly = true)
    public List<Customer> findAllCustomersByEmailContains(String emailPart) {
        return customerRepository.findAllByEmailContains(emailPart);
    }

    @Transactional(readOnly = true)
    public ShoppingCart findCustomerShoppingCartByIds(Long customerId, Long shoppingCartId) {
        ShoppingCart existingShoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart with id " + shoppingCartId + " not found"));

        if (!existingShoppingCart.getCustomer().getId().equals(customerId))
            throw new ResourceNotFoundException("Shopping Cart with id " + customerId + " not found");

        return existingShoppingCart;
    }

    @Transactional(readOnly = true)
    public PurchaseOrder findCustomerPurchaseOrderByIds(Long customerId, Long purchaseOrderId) {
        PurchaseOrder existingPurchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order with id " + purchaseOrderId + " not found"));

        if (!existingPurchaseOrder.getCustomer().getId().equals(customerId))
            throw new ResourceNotFoundException("Purchase Order with id " + purchaseOrderId + " not found");

        return existingPurchaseOrder;
    }

    @Transactional(readOnly = true)
    public List<ShoppingCart> findAllCustomerShoppingCartsById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"))
                .getShoppingCarts();
    }

    @Transactional(readOnly = true)
    public List<PurchaseOrder> findAllCustomerPurchaseOrdersById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"))
                .getPurchaseOrders();
    }

    //----POST----//
    @Transactional
    public Customer saveCustomer(CustomerCreateDto customerCreateDto) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return customerRepository.save(modelMapper.map(customerCreateDto, Customer.class));
    }

    //----PATCH----//
    @Transactional
    public Customer editCustomerById(Long id, CustomerUpdateDto customerUpdateDto) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));
        nonNullMapper.map(customerUpdateDto, existingCustomer);

        return customerRepository.save(existingCustomer);
    }

    //----DELETE----//
    @Transactional
    public void deleteCustomerById(Long id) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        if (!customerRepository.existsById(id))
            throw new ResourceNotFoundException("Customer with id " + id + " not found");

        customerRepository.deleteById(id);
    }


    //--------ANYONE--------//
    //----POST----//
    @Transactional
    public CustomerResponseDto registerCustomer(CustomerCreateDto customerCreateDto) {
        Customer customer = customerRepository.save(modelMapper.map(customerCreateDto, Customer.class));

        return modelMapper.map(customer, CustomerResponseDto.class);
    }


    //--------CUSTOMER--------//
    //----GET----//
    @Transactional(readOnly = true)
    public ShoppingCart findShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart with id " + id + " not foun"));
    }

    @Transactional(readOnly = true)
    public BigDecimal findTotalAmountById(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart with " + id + " not found"));

        return shoppingCart.getOrderItems().stream()
                .map(orderItem -> orderItem.getAmount().multiply(new BigDecimal(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //----POST----//
    @Transactional
    public ShoppingCart createShoppingCartByIds(Long customerId, List<Long> productIds) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        List<Product> products = productRepository.findAllById(productIds);

        ShoppingCart shoppingCart = new ShoppingCart();

        List<OrderItem> orderItems = products.stream().map(product -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setAmount(product.getPrice());
            orderItem.setQuantity(1);
            orderItem.setShoppingCart(shoppingCart);
            return orderItem;
        }).collect(Collectors.toList());

        shoppingCart.setOrderItems(orderItems);

        return shoppingCartRepository.save(shoppingCart);
    }

    //----PATCH----//
    @Transactional
    public CustomerResponseDto updateCustomerById(Long id, CustomerUpdateDto customerUpdateDto) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));

        nonNullMapper.map(customerUpdateDto, existing);

        customerRepository.save(existing);

        return modelMapper.map(existing, CustomerResponseDto.class);
    }

    @Transactional
    public ShoppingCart updateShoppingCartByIds(Long id, List<Long> productIds) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart with id " + id + " not found"));
        List<Product> products = productRepository.findAllById(productIds);

        List<OrderItem> orderItems = products.stream().map(product -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setAmount(product.getPrice());
            orderItem.setQuantity(1);
            orderItem.setShoppingCart(shoppingCart);
            return orderItem;
        }).collect(Collectors.toList());

        shoppingCart.setOrderItems(orderItems);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
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
    @Transactional
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