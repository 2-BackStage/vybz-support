package back.vybz.support_service.settlement.application;

import back.vybz.support_service.membership.domain.MemberShipStatus;
import back.vybz.support_service.membership.infrastructure.MemberShipRepository;
import back.vybz.support_service.support.domain.mysql.DonationState;
import back.vybz.support_service.support.infrastructure.mysql.DonationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final DonationHistoryRepository donationHistoryRepository;

    private final MemberShipRepository memberShipRepository;

    public int calculateTotalIncome(String buskerUuid) {
        // 후원받은 티켓 수 계산
        int ticketCount = donationHistoryRepository
                .sumTicketAmountByBuskerUuidAndDonationState(buskerUuid, DonationState.DONATION);
        int ticketIncome = ticketCount * 100;

        // 구독 수 계산
        int membershipGross = memberShipRepository
                .sumPriceByBuskerUuidAndStatus(buskerUuid, MemberShipStatus.SUCCESS);

        int membershipIncome = (int) Math.floor(membershipGross * 0.9);

        return ticketIncome + membershipIncome;
    }
}
