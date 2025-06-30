package back.vybz.support_service.settlement.infrastructure;

import back.vybz.support_service.settlement.domain.Settlement;
import back.vybz.support_service.settlement.domain.SettlementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    @Query("SELECT s FROM Settlement s WHERE s.buskerUuid = :buskerUuid ORDER BY s.createdAt DESC")
    Page<Settlement> findByBuskerUuidOrderByCreatedAtDesc(@Param("buskerUuid") String buskerUuid, Pageable pageable);

    @Query("SELECT COALESCE(SUM(s.amount), 0) FROM Settlement s WHERE s.buskerUuid = :buskerUuid AND s.settlementStatus = :status")
    Integer sumAmountByBuskerUuidAndStatus(@Param("buskerUuid") String buskerUuid, @Param("status") SettlementStatus status);

    Optional<Settlement> findBySettlementsUuid(String settlementsUuid);

    boolean existsBySettlementsUuid(String settlementsUuid);

}
