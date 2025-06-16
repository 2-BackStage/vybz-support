package back.vybz.support_service.kafka.config;

import back.vybz.support_service.kafka.event.ChargePaymentEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class ChargePaymentKafkaConfig {

    private final CommonKafkaConfig commonKafkaConfig;

    @Bean
    public ProducerFactory<String, ChargePaymentEvent> userInfoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonKafkaConfig.commonProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, ChargePaymentEvent> chargePaymentKafkaTemplate() {
        return new KafkaTemplate<>(userInfoProducerFactory());
    }

    @Bean
    public ProducerFactory<String, String> stringChargePaymentProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonKafkaConfig.commonProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> stringChargePaymentKafkaTemplate() {
        return new KafkaTemplate<>(stringChargePaymentProducerFactory());
    }


    @Bean
    public ConsumerFactory<String, ChargePaymentEvent> chargePaymentEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(ChargePaymentEvent.class, false))
        );
    }

    @Bean(name = "chargePaymentKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ChargePaymentEvent> chargePaymentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChargePaymentEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(chargePaymentEventConsumerFactory());

        return factory;
    }
}
