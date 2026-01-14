package com.pro.product_service.service;

import com.pro.product_service.dto.ProductEvent;
import com.pro.product_service.dto.ProductRequest;
import com.pro.product_service.dto.ProductResponse;
import com.pro.product_service.entity.Product;
import com.pro.product_service.kafka.KafkaProducerService;
import com.pro.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final KafkaProducerService kafkaProducerService;

    public ProductResponse createProduct(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        Product savedProduct = productRepository.save(product);
        log.info("Product created: {}",savedProduct.getId());

        return mapToResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return mapToResponse(product);
    }

    @Transactional
    public ProductResponse updateQuantity(Long id,Integer newQuantity){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "+ id));

        product.setQuantity(newQuantity);
        Product updateProduct = productRepository.save(product);

        ProductEvent event = new ProductEvent(id,"UPDATE_QUANTITY",newQuantity);
        kafkaProducerService.sendProductEvent(event);

        log.info("Product quantity updated and event sent : {}",id);
        return mapToResponse(updateProduct);
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
        log.info("Product deleted: {}",id);
    }

    private ProductResponse mapToResponse(Product product){
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }
}
