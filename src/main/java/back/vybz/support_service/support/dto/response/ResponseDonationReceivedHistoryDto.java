package back.vybz.support_service.support.dto.response;

import back.vybz.support_service.support.vo.response.ResponseDonationReceivedHistoryVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class   ResponseDonationReceivedHistoryDto {

    private String userUuid;

    private String nickname;

    private String profileImageUrl;

    private Integer ticketCount;

    private String message;

    private String receivedAt;

    @Builder
    public ResponseDonationReceivedHistoryDto(String userUuid, String nickname,
                                              String profileImageUrl, Integer ticketCount,
                                              String message, String receivedAt) {
        this.userUuid = userUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.ticketCount = ticketCount;
        this.message = message;
        this.receivedAt = receivedAt;
    }

    public ResponseDonationReceivedHistoryVo toResponseDonationReceivedHistoryVo() {
        return ResponseDonationReceivedHistoryVo.builder()
                .userUuid(userUuid)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .ticketCount(ticketCount)
                .message(message)
                .receivedAt(receivedAt)
                .build();
    }
}
