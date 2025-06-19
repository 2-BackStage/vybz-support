package back.vybz.support_service.support.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDonationReceivedHistoryVo {

    private String userUuid;

    private String nickname;

    private String profileImageUrl;

    private Integer ticketCount;

    private String message;

    private String receivedAt;

    @Builder
    public ResponseDonationReceivedHistoryVo(String userUuid, String nickname,
                                             String profileImageUrl, Integer ticketCount,
                                             String message, String receivedAt) {
        this.userUuid = userUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.ticketCount = ticketCount;
        this.message = message;
        this.receivedAt = receivedAt;
    }
}
