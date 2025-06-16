package back.vybz.support_service.support.application;

import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.common.exception.BaseException;
import back.vybz.support_service.support.domain.DonationHistory;
import back.vybz.support_service.support.domain.DonationState;
import back.vybz.support_service.support.domain.DonationWallet;
import back.vybz.support_service.support.dto.request.RequestDonationDto;
import back.vybz.support_service.support.infrastructure.DonationHistoryRepository;
import back.vybz.support_service.support.infrastructure.DonationWalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationWalletRepository donationWalletRepository;

    private final DonationHistoryRepository donationHistoryRepository;

    @Transactional
    @Override
    public void useDonateVTicket(RequestDonationDto requestDonationDto) {

        DonationWallet donationWallet = donationWalletRepository.findByUserUuid(requestDonationDto.getUserUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.DONATION_WALLET_NOT_FOUND));

        log.info("🎯 기존 티켓 수: {}", donationWallet.getTicketCount());

        donationWallet.useTickets(requestDonationDto.getTicketAmount());

        log.info("후원 티켓 수 : {}", requestDonationDto.getTicketAmount());

        donationWalletRepository.save(donationWallet);

        DonationHistory donation = DonationHistory.builder()
                .donationReceivedUuid(UUID.randomUUID().toString())
                .userUuid(requestDonationDto.getUserUuid())
                .buskerUuid(requestDonationDto.getBuskerUuid())
                .ticketAmount(requestDonationDto.getTicketAmount())
                .donationState(DonationState.DONATION)
                .message(requestDonationDto.getMessage())
                .build();

        log.info("✅ 후원 후 남은 티켓 수: {}", donationWallet.getTicketCount());

        donationHistoryRepository.save(donation);
    }
}
