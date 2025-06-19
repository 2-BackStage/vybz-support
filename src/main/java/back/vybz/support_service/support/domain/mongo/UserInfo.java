package back.vybz.support_service.support.domain.mongo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfo {

    private String userNickname;

    private String userProfileImageUrl;

    @Builder
    public UserInfo(String userNickname, String userProfileImageUrl) {
        this.userNickname = userNickname;
        this.userProfileImageUrl = userProfileImageUrl;
    }
}
