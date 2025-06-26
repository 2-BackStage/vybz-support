package back.vybz.support_service.kafka.consumer;

import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.common.exception.BaseException;
import back.vybz.support_service.kafka.event.SubscriptionCancelEvent;
import back.vybz.support_service.kafka.event.SubscriptionEvent;
import back.vybz.support_service.membership.domain.MemberShip;
import back.vybz.support_service.membership.domain.MemberShipStatus;
import back.vybz.support_service.membership.infrastructure.MemberShipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionEventConsumer {

    private final MemberShipRepository memberShipRepository;

    @KafkaListener(
            topics = "subscription-completed",
            groupId = "subscription-group",
            containerFactory = "subscriptionEventKafkaListenerContainerFactory"
    )
    public void consumeSubscriptionEvent(SubscriptionEvent subscriptionEvent) {

        log.info("🔥 Kafka 멤버십 메시지 수신: {}", subscriptionEvent);

        memberShipRepository.save(MemberShip.builder()
                .userUuid(subscriptionEvent.getUserUuid())
                .buskerUuid(subscriptionEvent.getBuskerUuid())
                .price(subscriptionEvent.getPrice())
                .memberShipStatus(MemberShipStatus.SUCCESS)
                .build()
        );
    }

    @KafkaListener(
            topics = "cancel-subscription",
            groupId = "cancel-subscription-group",
            containerFactory = "subscriptionCancelEventKafkaListenerContainerFactory"
    )
    public void consumeSubscriptionCancelEvent(SubscriptionCancelEvent subscriptionCancelEvent) {

        log.info("🔥 Kafka 멤버십 해지 메시지 수신: {}", subscriptionCancelEvent);

        MemberShip memberShip = memberShipRepository
                .findAllByUserUuidAndBuskerUuidAndDeletedFalse(
                        subscriptionCancelEvent.getUserUuid(),
                        subscriptionCancelEvent.getBuskerUuid()
                )
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_ACTIVE_MEMBERSHIP));

        memberShip.cancel();

        memberShipRepository.save(memberShip);
    }
}