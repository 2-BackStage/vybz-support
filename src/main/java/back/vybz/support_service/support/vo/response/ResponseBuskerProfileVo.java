package back.vybz.support_service.support.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ResponseBuskerProfileVo {

    private String profileImageUrl;

    private String nickname;

    @Builder
    public ResponseBuskerProfileVo(String profileImageUrl, String nickname) {
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
    }
}
