package com.proyecto.gestock.product.domain;

import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.product.dto.ProductDisplay;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import com.proyecto.gestock.product.infrastructure.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    //----ADMIN----//
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    public List<Product> findAllProductsByCategoryId(Long id) {
        return productRepository.findAllByCategoryId(id);
    }

    public List<Product> findAllProductsByStockGreaterThanEqual(Integer stock) {
        return productRepository.findAllByStockGreaterThanEqual(stock);
    }

    public List<Product> findAllProductsByStockLessThanEqual(Integer stock) {
        return productRepository.findAllByStockLessThanEqual(stock);
    }

    public List<Product> findAllProductsByAvailable(Boolean available) {
        return productRepository.findAllByAvailable(available);
    }

    //--------CUSTOMER--------//
    public ProductDisplayDto findValidProductByName(String name) {
        return modelMapper.map(productRepository.findByNameAndAvailableTrueAndStockGreaterThan(name, 0), ProductDisplayDto.class);
    }
    public List<ProductDisplayDto> findAllValidProductsByCategoryName(String categoryName) {
        List<ProductDisplay> productDisplayList = productRepository.findAllByAvailableTrueAndCategoryNameAndStockGreaterThan(categoryName , 0);

        return productDisplayList.stream()
                .map(productDisplay -> modelMapper.map(productDisplay, ProductDisplayDto.class))
                .collect(Collectors.toList());
    }



//    public List<ProductDisplayDto> getAllProducts(){
//        List<Product> products = productRepository.findAll();
//        return products.stream()
//                .map(product -> modelMapper.map(product, ProductDisplayDto.class))
//                .collect(Collectors.toList());
//    }
//
//
//    public ProductUpdateDto getProductById(Long id){
//        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with that id"));
//        return modelMapper.map(product, ProductUpdateDto.class);
//    }
//
//
//    public Product save(Product product) {
//        return productRepository.save(product);
//    }
//
//
//    public Product update(Long id, Product product) {
//        return productRepository.findById(id)
//                .map(existing -> {
//                    existing.setName(product.getName());
//                    existing.setDescription(product.getDescription());
//                    existing.setPrice(product.getPrice());
//                    existing.setStock(product.getStock());
//                    existing.setCategory(product.getCategory());
//                    return productRepository.save(existing);
//                }).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
//    }
//
//
//    public void deleteById(Long id) {
//        productRepository.findById(id).map(product -> {
//            productRepository.deleteById(id);
//            return product;
//        }).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
//    }
//
//    public boolean existsById(Long id) {
//        return productRepository.existsById(id);
//    }
//
//    public ProductUpdateDto createProduct(ProductUpdateDto productUpdateDto){
//        Product product = modelMapper.map(productUpdateDto, Product.class);
//        product = productRepository.save(product);
//        return modelMapper.map(product, ProductUpdateDto.class);
//    }
//
//    public ProductUpdateDto updateProduct(Long id, ProductUpdateDto productUpdateDto){
//        if(!productRepository.existsById(id)){
//            throw new ResourceNotFoundException("Product not found with that id");
//        }
//        Product product = modelMapper.map(productUpdateDto, Product.class);
//        product.setId(id);
//        product = productRepository.save(product);
//        return modelMapper.map(product, ProductUpdateDto.class);
//    }
//
//    public void deleteProduct(Long id){
//        if(!productRepository.existsById(id)){
//            throw new ResourceNotFoundException("Product not found with that id");
//        }
//        productRepository.deleteById(id);
//    }
}
