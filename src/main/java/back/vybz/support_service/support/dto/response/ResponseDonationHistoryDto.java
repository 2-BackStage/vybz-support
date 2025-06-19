package back.vybz.support_service.support.dto.response;

import back.vybz.support_service.support.vo.response.ResponseDonationHistoryVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDonationHistoryDto {

    private String buskerUuid;

    private String nickname;

    private String profileImageUrl;

    private Integer ticketCount;

    private String message;

    private String donatedAt;

    @Builder
    public ResponseDonationHistoryDto(String buskerUuid, String nickname,
                                      String profileImageUrl, Integer ticketCount,
                                      String message, String donatedAt) {
        this.buskerUuid = buskerUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.ticketCount = ticketCount;
        this.message = message;
        this.donatedAt = donatedAt;
    }

    public ResponseDonationHistoryVo toResponseDonationHistoryVo() {
        return ResponseDonationHistoryVo.builder()
                .buskerUuid(buskerUuid)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .ticketCount(ticketCount)
                .message(message)
                .donatedAt(donatedAt)
                .build();
    }
}
