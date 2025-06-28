package back.vybz.support_service.membership.infrastructure;

import back.vybz.support_service.membership.domain.MemberShip;
import back.vybz.support_service.membership.domain.MemberShipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberShipRepository extends JpaRepository<MemberShip, Long> {

    boolean existsByUserUuidAndBuskerUuidAndDeletedFalse(String userUuid, String buskerUuid);

    Optional<MemberShip> findAllByUserUuidAndBuskerUuidAndDeletedFalse(String userUuid, String buskerUuid);

    List<MemberShip> findByUserUuidAndMemberShipStatusAndDeletedFalse(String userUuid, MemberShipStatus memberShipStatus);

    List<MemberShip> findByUserUuidAndMemberShipStatusAndDeletedTrue(String userUuid, MemberShipStatus memberShipStatus);
}
