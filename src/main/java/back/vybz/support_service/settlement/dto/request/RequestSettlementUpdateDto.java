package back.vybz.support_service.settlement.dto.request;

import back.vybz.support_service.settlement.vo.request.RequestSettlementUpdateVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSettlementUpdateDto {

    private String bankName;

    private String accountNumber;

    private String depositName;

    private Integer amount;

    @Builder
    public RequestSettlementUpdateDto(String bankName, String accountNumber, String depositName, Integer amount) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.depositName = depositName;
        this.amount = amount;
    }

    public static RequestSettlementUpdateDto from(RequestSettlementUpdateVo requestSettlementUpdateVo) {
        return RequestSettlementUpdateDto.builder()
                .bankName(requestSettlementUpdateVo.getBankName())
                .accountNumber(requestSettlementUpdateVo.getAccountNumber())
                .depositName(requestSettlementUpdateVo.getDepositName())
                .amount(requestSettlementUpdateVo.getAmount())
                .build();
    }
}
