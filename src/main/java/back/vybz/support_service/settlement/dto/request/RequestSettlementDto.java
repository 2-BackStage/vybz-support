package back.vybz.support_service.settlement.dto.request;

import back.vybz.support_service.settlement.domain.Settlement;
import back.vybz.support_service.settlement.vo.request.RequestSettlementVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSettlementDto {

    private String bankName;

    private String accountNumber;

    private String depositName;

    private Integer amount;

    @Builder
    public RequestSettlementDto(String bankName, String accountNumber, String depositName, Integer amount) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.depositName = depositName;
        this.amount = amount;
    }

    public static RequestSettlementDto fromSettlement(RequestSettlementVo requestSettlement) {
        return RequestSettlementDto.builder()
                .bankName(requestSettlement.getBankName())
                .accountNumber(requestSettlement.getAccountNumber())
                .depositName(requestSettlement.getDepositName())
                .amount(requestSettlement.getAmount())
                .build();
    }
}
