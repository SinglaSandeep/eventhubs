package com.azure.eventhub.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {


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
