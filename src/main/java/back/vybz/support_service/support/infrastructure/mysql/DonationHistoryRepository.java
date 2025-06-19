package back.vybz.support_service.support.infrastructure.mysql;

import back.vybz.support_service.support.domain.mysql.DonationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Long> {
}
