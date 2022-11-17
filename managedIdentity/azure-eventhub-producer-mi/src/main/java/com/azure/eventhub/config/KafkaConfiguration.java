package com.azure.eventhub.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.server}")
    private String bootStrapServer;
    @Value("${sasl.jaas.config}")
    private String saslConfig;
    @Value("${sasl.login.callback.handler.class}")
    private String saslCallbackHandler;
    @Value("${sasl.mechanism}")
    private String saslMechanism;
    @Value("${security.protocol}")
    private String securityProtocol;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        addSaslProperties(props, saslMechanism, securityProtocol, saslConfig, saslCallbackHandler);
        return new DefaultKafkaProducerFactory<>(props);

    }

    public static void addSaslProperties(Map<String, Object> properties, String saslMechanism,
                                         String securityProtocol, String saslConfig, String saslCallbackHandler
    ) {
        properties.put("security.protocol", securityProtocol);
        properties.put("sasl.mechanism", saslMechanism);
        properties.put("sasl.jaas.config", saslConfig);
        properties.put("sasl.login.callback.handler.class", saslCallbackHandler);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


}