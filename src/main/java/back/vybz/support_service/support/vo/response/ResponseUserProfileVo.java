package back.vybz.support_service.support.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ResponseUserProfileVo {

    private String profileImageUrl;

    private String nickname;

    @Builder
    public ResponseUserProfileVo(String profileImageUrl, String nickname) {
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
    }
}
