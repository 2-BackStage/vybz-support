package back.vybz.support_service.kafka.producer;

import back.vybz.support_service.kafka.event.SubscribeCountEvent;
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
public class SubscribeCountEventProducer {

    private final KafkaTemplate<String, SubscribeCountEvent> subscribeCountEventKafkaTemplate;

    public static final String CREATE_USER_TOPIC = "subscribe-count-events";

    public void sendSubscribeCountEvent(SubscribeCountEvent subscribeCountEvent) {
        log.info("[Kafka] Sending SubscribeCountEvent to topic '{}': {}", CREATE_USER_TOPIC, subscribeCountEvent);

        try {
            CompletableFuture<SendResult<String, SubscribeCountEvent>> future = subscribeCountEventKafkaTemplate.send(CREATE_USER_TOPIC, subscribeCountEvent);

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("[Kafka] Failed to send SubscribeCountEvent: {}", ex.getMessage(), ex);

                } else {
                    log.info("[Kafka] Successfully sent SubscribeCountEvent. Topic: {}, Partition: {}, Offset: {}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                }
            });
        } catch (Exception e) {
            log.error("[Kafka] Failed to send SubscribeCountEvent: {}", e.getMessage(), e);
        }
    }
}
