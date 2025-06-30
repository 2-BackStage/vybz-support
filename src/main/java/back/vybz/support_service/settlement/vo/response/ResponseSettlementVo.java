package back.vybz.support_service.settlement.vo.response;

import back.vybz.support_service.settlement.domain.SettlementStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseSettlementVo {

    private String settlementsUuid;

    private String buskerUuid;

    private Integer amount;

    private SettlementStatus settlementStatus;

    @Builder
    public ResponseSettlementVo(String settlementsUuid, String buskerUuid,
                                Integer amount, SettlementStatus settlementStatus) {
        this.settlementsUuid = settlementsUuid;
        this.buskerUuid = buskerUuid;
        this.amount = amount;
        this.settlementStatus = settlementStatus;
    }
}
