package com.pro.inventory_service.kafka;

import com.pro.inventory_service.dto.ProductEvent;
import com.pro.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "product-events", groupId = "inventory-group")
    public void consumerProductEvent(ProductEvent event){
        log.info("===== Consuming event from Kafka: {} =====", event);

        try {
            if ("UPDATE_QUANTITY".equals(event.getAction())){
                inventoryService.updateProductQuantity(
                        event.getProductId(),
                        event.getNewQuantity()
                );

                log.info("Event processed successfully");
            }
        }catch(Exception e){
            log.error("Error processing event: {}",e.getMessage(),e);
        }
    }
}
