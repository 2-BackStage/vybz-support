package back.vybz.support_service.support.domain.mongo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BuskerInfo {

    private String buskerNickname;

    private String buskerProfileImageUrl;

    @Builder
    public BuskerInfo(String buskerNickname, String buskerProfileImageUrl) {
        this.buskerNickname = buskerNickname;
        this.buskerProfileImageUrl = buskerProfileImageUrl;
    }
}
