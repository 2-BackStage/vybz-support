package back.vybz.support_service.kafka.consumer;

import back.vybz.support_service.kafka.event.ChargePaymentEvent;
import back.vybz.support_service.support.domain.DonationHistory;
import back.vybz.support_service.support.domain.DonationState;
import back.vybz.support_service.support.domain.DonationWallet;
import back.vybz.support_service.support.infrastructure.DonationHistoryRepository;
import back.vybz.support_service.support.infrastructure.DonationWalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChargePaymentEventConsumer {

    private final DonationHistoryRepository donationHistoryRepository;

    private final DonationWalletRepository donationWalletRepository;

    @KafkaListener(
            topics = "create-payment-confirm",
            groupId = "charge-payment-group",
            containerFactory = "chargePaymentKafkaListenerContainerFactory"
    )
    public void consumeChargePaymentEvent(ChargePaymentEvent chargePaymentEvent) {

        log.info("🔥 Kafka 충전 티켓 메시지 수신: {}", chargePaymentEvent);

        donationHistoryRepository.save(DonationHistory.builder()
                .userUuid(chargePaymentEvent.getUserUuid())
                .ticketAmount(chargePaymentEvent.getTicketCount())
                .donationState(DonationState.CHARGE)
                .build()
        );

        DonationWallet donationWallet = donationWalletRepository.findByUserUuid(chargePaymentEvent.getUserUuid())
                .orElse(null);

        if (donationWallet == null) {
            donationWallet = DonationWallet.builder()
                    .userUuid(chargePaymentEvent.getUserUuid())
                    .ticketCount(chargePaymentEvent.getTicketCount())
                    .build();
        } else {
            donationWallet.addTickets(chargePaymentEvent.getTicketCount());
        }

        donationWalletRepository.save(donationWallet);
    }
}
