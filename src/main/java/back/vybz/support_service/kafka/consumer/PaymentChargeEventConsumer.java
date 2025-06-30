package back.vybz.support_service.kafka.consumer;

import back.vybz.support_service.kafka.event.ChargePaymentEvent;
import back.vybz.support_service.kafka.event.PaymentRefundEvent;
import back.vybz.support_service.kafka.event.TicketChangedEvent;
import back.vybz.support_service.kafka.producer.VTicketKafkaEventProducer;
import back.vybz.support_service.support.domain.mysql.DonationHistory;
import back.vybz.support_service.support.domain.mysql.DonationState;
import back.vybz.support_service.support.domain.mysql.DonationWallet;
import back.vybz.support_service.support.infrastructure.mysql.DonationHistoryRepository;
import back.vybz.support_service.support.infrastructure.mysql.DonationWalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentChargeEventConsumer {

    private final DonationHistoryRepository donationHistoryRepository;

    private final DonationWalletRepository donationWalletRepository;

    private final VTicketKafkaEventProducer vTicketKafkaEventProducer;

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
                .amount(chargePaymentEvent.getAmount())
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

        vTicketKafkaEventProducer.sendPaymentConfirmEvent(TicketChangedEvent.builder()
                .userUuid(chargePaymentEvent.getUserUuid())
                .ticketCount(donationWallet.getTicketCount())
                .build());
    }

    @KafkaListener(
            topics = "update-payment",
            groupId = "refund-payment-group",
            containerFactory = "paymentRefundKafkaListenerContainerFactory"
    )
    public void consumeRefundPaymentEvent(PaymentRefundEvent paymentRefundEvent) {

        log.info("🔥 Kafka 환불 티켓 메시지 수신: {}", paymentRefundEvent);

        // 환불 내역 저장
        donationHistoryRepository.save(DonationHistory.builder()
                .userUuid(paymentRefundEvent.getUserUuid())
                .ticketAmount(paymentRefundEvent.getTicketCount())
                .amount(paymentRefundEvent.getAmount())
                .donationState(DonationState.REFUND)
                .build()
        );

        donationWalletRepository.findByUserUuid(paymentRefundEvent.getUserUuid()).ifPresent(wallet -> {
            wallet.ticketsByRefund(paymentRefundEvent.getTicketCount());

            donationWalletRepository.save(wallet);
            
            // 환불 후 전체 티켓 수 전송
            vTicketKafkaEventProducer.sendPaymentConfirmEvent(TicketChangedEvent.builder()
                    .userUuid(paymentRefundEvent.getUserUuid())
                    .ticketCount(wallet.getTicketCount())
                    .build());
        });
    }
}
