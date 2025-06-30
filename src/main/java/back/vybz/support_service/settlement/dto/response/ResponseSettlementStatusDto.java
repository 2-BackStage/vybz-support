package back.vybz.support_service.settlement.dto.response;

import back.vybz.support_service.settlement.vo.response.ResponseSettlementStatusVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseSettlementStatusDto {

    private String buskerUuid;

    private Integer totalIncome;

    private Integer availableAmount;

    private Integer pendingAmount;

    private Integer completedAmount;

    @Builder
    public ResponseSettlementStatusDto(String buskerUuid, Integer totalIncome, Integer availableAmount,
                                       Integer pendingAmount, Integer completedAmount) {
        this.buskerUuid = buskerUuid;
        this.totalIncome = totalIncome;
        this.availableAmount = availableAmount;
        this.pendingAmount = pendingAmount;
        this.completedAmount = completedAmount;
    }

    public static ResponseSettlementStatusDto from(ResponseSettlementStatusVo responseSettlementStatusVo) {
        return ResponseSettlementStatusDto.builder()
                .buskerUuid(responseSettlementStatusVo.getBuskerUuid())
                .totalIncome(responseSettlementStatusVo.getTotalIncome())
                .availableAmount(responseSettlementStatusVo.getAvailableAmount())
                .pendingAmount(responseSettlementStatusVo.getPendingAmount())
                .completedAmount(responseSettlementStatusVo.getCompletedAmount())
                .build();
    }
}