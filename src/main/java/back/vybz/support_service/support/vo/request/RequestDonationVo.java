package back.vybz.support_service.support.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestDonationVo {

    private String userUuid;

    private String buskerUuid;

    private Integer ticketAmount;

    private String message;

    @Builder
    public RequestDonationVo(String userUuid, String buskerUuid, Integer ticketAmount, String message) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.ticketAmount = ticketAmount;
        this.message = message;
    }
}
