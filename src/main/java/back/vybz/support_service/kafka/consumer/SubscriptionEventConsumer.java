package back.vybz.support_service.kafka.consumer;

import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.common.exception.BaseException;
import back.vybz.support_service.kafka.event.SubscribeCountEvent;
import back.vybz.support_service.kafka.event.SubscriptionCancelEvent;
import back.vybz.support_service.kafka.event.SubscriptionEvent;
import back.vybz.support_service.kafka.producer.SubscribeCountEventProducer;
import back.vybz.support_service.membership.domain.MemberShip;
import back.vybz.support_service.membership.domain.MemberShipStatus;
import back.vybz.support_service.membership.infrastructure.MemberShipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionEventConsumer {

    private final MemberShipRepository memberShipRepository;

    private final SubscribeCountEventProducer subscribeCountEventProducer;

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

        // ✅ 현재 유저의 구독 수 조회
        int count = (int) memberShipRepository.countByUserUuidAndStatus(subscriptionEvent.getUserUuid(), MemberShipStatus.SUCCESS);
        log.info("🌟 유저 구독 수 조회 : {}", count);

        // ✅ Kafka 이벤트 전송
        subscribeCountEventProducer.sendSubscribeCountEvent(
                SubscribeCountEvent.builder()
                        .userUuid(subscriptionEvent.getUserUuid())
                        .subscriptionCount(count)
                        .build()
        );

        log.info("🔥 Kafka 이벤트 전송 : {}", subscriptionEvent);
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

        int count = (int) memberShipRepository.countByUserUuidAndStatus(
                subscriptionCancelEvent.getUserUuid(), MemberShipStatus.SUCCESS);

        subscribeCountEventProducer.sendSubscribeCountEvent(
                SubscribeCountEvent.builder()
                        .userUuid(subscriptionCancelEvent.getUserUuid())
                        .subscriptionCount(count)
                        .build()
        );
    }
}