package com.movie.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> consumerProps = new HashMap<>();
//        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
//        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
//        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
//        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        consumerProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.event.dto.NotificationEvent");
//        return consumerProps;
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, NotificationEvent> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, NotificationEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//    }
}
