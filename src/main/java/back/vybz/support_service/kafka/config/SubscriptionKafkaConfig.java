package back.vybz.support_service.kafka.config;

import back.vybz.support_service.kafka.event.ChargePaymentEvent;
import back.vybz.support_service.kafka.event.SubscriptionCancelEvent;
import back.vybz.support_service.kafka.event.SubscriptionEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class SubscriptionKafkaConfig {

    private final CommonKafkaConfig commonKafkaConfig;

    @Bean
    public ConsumerFactory<String, SubscriptionEvent> SubscriptionEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(SubscriptionEvent.class, false))
        );
    }

    @Bean(name = "subscriptionEventKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, SubscriptionEvent> subscriptionEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SubscriptionEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(SubscriptionEventConsumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, SubscriptionCancelEvent> SubscriptionCancelEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(SubscriptionCancelEvent.class, false))
        );
    }

    @Bean(name = "subscriptionCancelEventKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, SubscriptionCancelEvent> subscriptionCancelEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SubscriptionCancelEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(SubscriptionCancelEventConsumerFactory());

        return factory;
    }
}
