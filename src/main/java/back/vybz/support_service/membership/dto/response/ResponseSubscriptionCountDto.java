package back.vybz.support_service.membership.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseSubscriptionCountDto {
    
    private String buskerUuid;
    
    private Integer subscriptionCount;

    @Builder
    public ResponseSubscriptionCountDto(String buskerUuid, Integer subscriptionCount) {
        this.buskerUuid = buskerUuid;
        this.subscriptionCount = subscriptionCount;
    }
}
