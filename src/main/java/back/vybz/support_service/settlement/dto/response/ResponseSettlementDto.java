package back.vybz.support_service.settlement.dto.response;

import back.vybz.support_service.settlement.domain.SettlementStatus;
import back.vybz.support_service.settlement.vo.response.ResponseSettlementVo;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ResponseSettlementDto {

    private String settlementsUuid;

    private String buskerUuid;

    private Integer amount;

    private SettlementStatus status;


    public static ResponseSettlementDto from(ResponseSettlementVo responseSettlementVo) {
        return ResponseSettlementDto.builder()
                .settlementsUuid(responseSettlementVo.getSettlementsUuid())
                .buskerUuid(responseSettlementVo.getBuskerUuid())
                .amount(responseSettlementVo.getAmount())
                .status(responseSettlementVo.getSettlementStatus())
                .build();
    }
}
