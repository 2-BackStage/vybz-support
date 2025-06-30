package back.vybz.support_service.support.infrastructure.mysql;

import back.vybz.support_service.support.domain.mysql.DonationHistory;
import back.vybz.support_service.support.domain.mysql.DonationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Long> {


    @Query("SELECT COALESCE(SUM(d.ticketAmount), 0) FROM DonationHistory d WHERE d.buskerUuid = :buskerUuid AND d.donationState = :state")
    int sumTicketAmountByBuskerUuidAndDonationState(@Param("buskerUuid") String buskerUuid,
                                                    @Param("state") DonationState state);
}
