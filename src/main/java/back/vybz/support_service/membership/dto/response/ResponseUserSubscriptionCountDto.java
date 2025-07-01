package back.vybz.support_service.membership.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseUserSubscriptionCountDto {

    private String userUuid;

    private Integer subscriptionCount;

    @Builder
    public ResponseUserSubscriptionCountDto(String userUuid, Integer subscriptionCount) {
        this.userUuid = userUuid;
        this.subscriptionCount = subscriptionCount;
    }
}
