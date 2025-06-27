package back.vybz.support_service.kafka.producer;

import back.vybz.support_service.kafka.event.TicketChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class VTicketKafkaEventProducer {

    private final KafkaTemplate<String, TicketChangedEvent> ticketKafkaTemplate;

    public static final String CREATE_USER_TOPIC = "create-ticket-changed";

    public void sendPaymentConfirmEvent(TicketChangedEvent ticketChangedEvent) {
        log.info("[Kafka] Sending TicketChangedEvent to topic '{}': {}", CREATE_USER_TOPIC, ticketChangedEvent);

        try {
            CompletableFuture<SendResult<String, TicketChangedEvent>> future = ticketKafkaTemplate.send(CREATE_USER_TOPIC, ticketChangedEvent);

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("[Kafka] Failed to send TicketChangedEvent: {}", ex.getMessage(), ex);

                } else {
                    log.info("[Kafka] Successfully sent TicketChangedEvent. Topic: {}, Partition: {}, Offset: {}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                }
            });
        } catch (Exception e) {
            log.error("[Kafka] Failed to send TicketChangedEvent: {}", e.getMessage(), e);
        }
    }

}
