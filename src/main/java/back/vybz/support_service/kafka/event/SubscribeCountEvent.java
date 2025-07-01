package back.vybz.support_service.kafka.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscribeCountEvent {

    private String userUuid;

    private Integer subscriptionCount;

    @Builder
    public SubscribeCountEvent(String userUuid, Integer subscriptionCount) {
        this.userUuid = userUuid;
        this.subscriptionCount = subscriptionCount;
    }
}
