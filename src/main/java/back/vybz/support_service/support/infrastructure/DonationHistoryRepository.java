package back.vybz.support_service.support.infrastructure;

import back.vybz.support_service.support.domain.DonationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Long> {
}
