package back.vybz.support_service.membership.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseBillingKeyDto {

    private String billingKey;

    @Builder
    public ResponseBillingKeyDto(String billingKey) {
        this.billingKey = billingKey;
    }
}
