package back.vybz.support_service.membership.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class RequestSubscribeVo {

    private String userUuid;

    private String buskerUuid;

    private Integer price;

    private String authKey;

    private String customerKey;

    @Builder
    public RequestSubscribeVo(String userUuid, String buskerUuid, Integer price,
                               String authKey, String customerKey) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.price = price;
        this.authKey = authKey;
        this.customerKey = customerKey;
    }
}
