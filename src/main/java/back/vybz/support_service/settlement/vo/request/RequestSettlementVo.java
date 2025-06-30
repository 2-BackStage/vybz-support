package back.vybz.support_service.settlement.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSettlementVo {

    private String bankName;

    private String accountNumber;

    private String depositName;

    private Integer amount;

    @Builder
    public RequestSettlementVo(String bankName, String accountNumber, String depositName, Integer amount) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.depositName = depositName;
        this.amount = amount;
    }
}
