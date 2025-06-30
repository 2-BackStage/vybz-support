package back.vybz.support_service.settlement.domain;

import back.vybz.support_service.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "settlement")
@Getter
@NoArgsConstructor
public class Settlement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "settlements_uuid", nullable = false, unique = true)
    private String settlementsUuid;

    @Column(name = "busker_uuid", nullable = false)
    private String buskerUuid;

    @Column(name = "deposit_name", nullable = false)
    private String depositName;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SettlementStatus settlementStatus;

    @Column(name = "reason")
    private String rejectReason;

    @Builder
    public Settlement(Long id, String settlementsUuid, String buskerUuid,
                     String depositName, String bankName, String accountNumber,
                     Integer amount, SettlementStatus settlementStatus, String rejectReason) {
        this.id = id;
        this.settlementsUuid = settlementsUuid;
        this.buskerUuid = buskerUuid;
        this.depositName = depositName;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.settlementStatus = settlementStatus;
        this.rejectReason = rejectReason;
    }

    public void updateSettlementInfo(String depositName, String bankName, String accountNumber, Integer amount) {
        this.depositName = depositName;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
}
