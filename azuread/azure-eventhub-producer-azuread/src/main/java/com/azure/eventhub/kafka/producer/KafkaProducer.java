package com.azure.eventhub.kafka.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("startKafkaSend")
@Component
public class KafkaProducer {
    @Autowired
    private Customer customer;
    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    public KafkaProducer(KafkaTemplate<String, String>
                                 kafkaTemplate,
                         @Value("${spring.kafka.producer.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @GetMapping("/{count}")
    public String sendKafka(@PathVariable(value = "count") long messageCount) throws ExecutionException, InterruptedException, JsonProcessingException {

        long start = System.currentTimeMillis();
        int i = 0;
        int success = 0;
        int failed = 0;
        System.out.println("Start Sending Message at " + System.currentTimeMillis());
        while (i < messageCount) {
            customer.setItemName("Item" + i);
            customer.setPrice(BigDecimal.valueOf(i));
            customer.setQuantity(i);
            customer.setCustomerID(start);
            customer.setCustomerName("Customer" + i);
            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(customer);
            try {
                kafkaTemplate.send(topic, requestJson);
                success = success + 1;
            } catch (Exception e) {
                System.out.println(e);
                failed = failed + 1;
            }
            i = i + 1;

        }
        String response = "Successfully send Messages " + success;
        System.out.println("Finished sending Messages to Eventhub" + "Success:" + success + "Failed:" + failed);
        return response;
    }
}

