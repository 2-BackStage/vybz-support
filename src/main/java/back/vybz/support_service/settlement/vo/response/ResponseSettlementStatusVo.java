package back.vybz.support_service.settlement.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseSettlementStatusVo {

    private String buskerUuid;

    private Integer totalIncome;

    private Integer availableAmount;

    private Integer pendingAmount;

    private Integer completedAmount;

    @Builder
    public ResponseSettlementStatusVo(String buskerUuid, Integer totalIncome, Integer availableAmount,
                                       Integer pendingAmount, Integer completedAmount) {
        this.buskerUuid = buskerUuid;
        this.totalIncome = totalIncome;
        this.availableAmount = availableAmount;
        this.pendingAmount = pendingAmount;
        this.completedAmount = completedAmount;
    }
}
