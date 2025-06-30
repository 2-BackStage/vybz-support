package back.vybz.support_service.settlement.dto.response;

import back.vybz.support_service.settlement.domain.SettlementStatus;
import back.vybz.support_service.settlement.vo.response.ResponseSettlementHistoryVo;
import back.vybz.support_service.support.vo.response.ResponseDonationHistoryVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponseSettlementHistoryDto {

    private String settlementsUuid;

    private String buskerUuid;

    private String depositName;

    private String bankName;

    private String accountNumber;

    private Integer amount;

    private SettlementStatus status;

    private String rejectReason;

    private String createdAt;

    private String updatedAt;

    @Builder
    public ResponseSettlementHistoryDto(String settlementsUuid, String buskerUuid, String depositName,
                                        String bankName, String accountNumber, Integer amount, SettlementStatus status,
                                        String rejectReason, String createdAt, String updatedAt) {
        this.settlementsUuid = settlementsUuid;
        this.buskerUuid = buskerUuid;
        this.depositName = depositName;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.status = status;
        this.rejectReason = rejectReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ResponseSettlementHistoryVo toResponseSettlementHistoryVo() {
        return ResponseSettlementHistoryVo.builder()
                .settlementsUuid(settlementsUuid)
                .buskerUuid(buskerUuid)
                .accountNumber(accountNumber)
                .amount(amount)
                .depositName(depositName)
                .bankName(bankName)
                .status(status)
                .rejectReason(rejectReason)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}