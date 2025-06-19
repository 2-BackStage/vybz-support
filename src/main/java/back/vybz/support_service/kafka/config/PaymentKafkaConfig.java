package back.vybz.support_service.kafka.config;

import back.vybz.support_service.kafka.event.ChargePaymentEvent;
import back.vybz.support_service.kafka.event.PaymentRefundEvent;
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
public class PaymentKafkaConfig {

    private final CommonKafkaConfig commonKafkaConfig;

    @Bean
    public ConsumerFactory<String, ChargePaymentEvent> PaymentChargeEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(ChargePaymentEvent.class, false))
        );
    }

    @Bean(name = "chargePaymentKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ChargePaymentEvent> chargePaymentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChargePaymentEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(PaymentChargeEventConsumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, PaymentRefundEvent> PaymentRefundEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(PaymentRefundEvent.class, false))
        );
    }

    @Bean(name = "paymentRefundKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, PaymentRefundEvent> paymentRefundKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentRefundEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(PaymentRefundEventConsumerFactory());

        return factory;
    }
}
