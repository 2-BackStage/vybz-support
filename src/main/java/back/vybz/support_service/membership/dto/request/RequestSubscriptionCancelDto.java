package back.vybz.support_service.membership.dto.request;

import back.vybz.support_service.membership.vo.request.RequestSubscribeVo;
import back.vybz.support_service.membership.vo.request.RequestSubscriptionCancelVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSubscriptionCancelDto {

    private String userUuid;

    private String buskerUuid;

    @Builder
    public RequestSubscriptionCancelDto(String userUuid, String buskerUuid) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
    }


    public static RequestSubscriptionCancelDto from (RequestSubscriptionCancelVo requestSubscriptionCancelVo) {
        return RequestSubscriptionCancelDto.builder()
                .userUuid(requestSubscriptionCancelVo.getUserUuid())
                .buskerUuid(requestSubscriptionCancelVo.getBuskerUuid())
                .build();
    }
}
