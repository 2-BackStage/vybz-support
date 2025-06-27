package back.vybz.support_service.kafka.config;

import back.vybz.support_service.kafka.event.TicketChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class VTicketKafkaConfig {

    private final CommonKafkaConfig commonKafkaConfig;

    @Bean
    public ProducerFactory<String, TicketChangedEvent> ticketKafkaProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonKafkaConfig.commonProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, TicketChangedEvent> ticketKafkaTemplate() {
        return new KafkaTemplate<>(ticketKafkaProducerFactory());
    }
}
