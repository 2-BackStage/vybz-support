package back.vybz.support_service.support.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDonationHistoryVo {

    private String buskerUuid;

    private String nickname;

    private String profileImageUrl;

    private Integer ticketCount;

    private String message;

    private String donatedAt;

    @Builder
    public ResponseDonationHistoryVo(String buskerUuid, String nickname,
                                      String profileImageUrl, Integer ticketCount,
                                      String message, String donatedAt) {
        this.buskerUuid = buskerUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.ticketCount = ticketCount;
        this.message = message;
        this.donatedAt = donatedAt;
    }
}
