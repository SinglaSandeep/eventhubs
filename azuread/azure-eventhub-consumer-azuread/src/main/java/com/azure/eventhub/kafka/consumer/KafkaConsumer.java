package com.azure.eventhub.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaConsumer {
    @Autowired
    private Customer customer;
    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.groupid} ", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord message, Acknowledgment acknowledgment) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Customer customer = objectMapper.readValue(message.value().toString(), Customer.class);
            System.out.println("Message & Current Offset" + customer.CustomerID + "   " + message.partition() + "  " + message.offset());

            // Commit after every 9 messages
            if (message.offset() % 9 == 0)
                acknowledgment.acknowledge();
        } catch (Exception e) {
            System.out.println(e);
            acknowledgment.acknowledge();
        }
    }

}
