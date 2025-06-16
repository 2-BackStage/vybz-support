package back.vybz.support_service.support.infrastructure;

import back.vybz.support_service.support.domain.DonationWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonationWalletRepository extends JpaRepository<DonationWallet, Long> {

    Optional<DonationWallet> findByUserUuid(String userUuid);

}
