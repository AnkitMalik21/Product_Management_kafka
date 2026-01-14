package com.pro.product_service.kafka;

import com.pro.product_service.dto.ProductEvent;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private static final String TOPIC = "product-events";
    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public void sendProductEvent(ProductEvent event){
        log.info("Producing event to Kafka: {}",event);
        kafkaTemplate.send(TOPIC,event);
        log.info("Event sent successfully updated topics: {}",TOPIC);
    }
}
