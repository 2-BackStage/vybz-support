package back.vybz.support_service.membership.infrastructure;

import back.vybz.support_service.membership.domain.MemberShip;
import back.vybz.support_service.membership.domain.MemberShipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberShipRepository extends JpaRepository<MemberShip, Long> {

    boolean existsByUserUuidAndBuskerUuidAndDeletedFalse(String userUuid, String buskerUuid);

    Optional<MemberShip> findAllByUserUuidAndBuskerUuidAndDeletedFalse(String userUuid, String buskerUuid);

    List<MemberShip> findByUserUuidAndMemberShipStatusAndDeletedFalse(String userUuid, MemberShipStatus memberShipStatus);

    List<MemberShip> findByUserUuidAndMemberShipStatusAndDeletedTrue(String userUuid, MemberShipStatus memberShipStatus);

    @Query(" SELECT COALESCE(SUM(m.price), 0) FROM MemberShip m WHERE m.buskerUuid = :buskerUuid AND m.memberShipStatus = :status AND m.deleted = false")
    int sumPriceByBuskerUuidAndStatus(@Param("buskerUuid") String buskerUuid, @Param("status") MemberShipStatus status);

    @Query(" SELECT COUNT(m) FROM MemberShip m WHERE m.buskerUuid = :buskerUuid AND m.memberShipStatus = :status AND m.deleted = false")
    long countByBuskerUuidAndStatus(@Param("buskerUuid") String buskerUuid, @Param("status") MemberShipStatus status);

    @Query(" SELECT COUNT(m) FROM MemberShip m WHERE m.userUuid = :userUuid AND m.memberShipStatus = :status AND m.deleted = false")
    long countByUserUuidAndStatus(@Param("userUuid") String userUuid, @Param("status") MemberShipStatus status);

}
