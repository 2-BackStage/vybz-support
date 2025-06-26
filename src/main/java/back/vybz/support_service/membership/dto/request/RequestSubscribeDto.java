package back.vybz.support_service.membership.dto.request;

import back.vybz.support_service.membership.vo.request.RequestSubscribeVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class RequestSubscribeDto {

    private String userUuid;

    private String buskerUuid;

    private Integer price;

    private String authKey;

    private String customerKey;

    @Builder
    public RequestSubscribeDto(String userUuid, String buskerUuid, Integer price,
                               String authKey, String customerKey) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.price = price;
        this.authKey = authKey;
        this.customerKey = customerKey;
    }

    public static RequestSubscribeDto from (RequestSubscribeVo requestSubscribeVo) {
        return RequestSubscribeDto.builder()
                .userUuid(requestSubscribeVo.getUserUuid())
                .buskerUuid(requestSubscribeVo.getBuskerUuid())
                .price(requestSubscribeVo.getPrice())
                .authKey(requestSubscribeVo.getAuthKey())
                .customerKey(requestSubscribeVo.getCustomerKey())
                .build();
    }
}
