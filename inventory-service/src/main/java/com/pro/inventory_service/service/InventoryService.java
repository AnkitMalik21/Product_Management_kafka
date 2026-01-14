package com.pro.inventory_service.service;

import com.pro.inventory_service.entity.Product;
import com.pro.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final ProductRepository productRepository;

    public void updateProductQuantity(Long productId,Integer newQuantity){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        product.setQuantity(newQuantity);

        if(newQuantity == 0){
            log.info("Quantity is 0. Deleting product: {}",productId);
            productRepository.delete(product);
        }else{
            productRepository.save(product);
            log.info("Updated product quantity: {}",product);
        }
    }
}
