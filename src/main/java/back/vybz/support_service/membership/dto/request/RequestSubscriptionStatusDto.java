package back.vybz.support_service.membership.dto.request;

import back.vybz.support_service.membership.domain.MemberShipStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class RequestSubscriptionStatusDto {

    private String userUuid;

    private String buskerUuid;

    private Integer price;

    private MemberShipStatus memberShipStatus;

    private String eventType; // "SUBSCRIPTION_CREATED", "SUBSCRIPTION_CANCELED"

    @Builder
    public RequestSubscriptionStatusDto(String userUuid, String buskerUuid, Integer price,
                                        MemberShipStatus memberShipStatus, String eventType) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.price = price;
        this.memberShipStatus = memberShipStatus;
        this.eventType = eventType;
    }
} 