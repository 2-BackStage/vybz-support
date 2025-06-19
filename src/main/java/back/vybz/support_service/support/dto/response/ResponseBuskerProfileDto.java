package back.vybz.support_service.support.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ResponseBuskerProfileDto {

    private String buskerUuid;

    private String profileImageUrl;

    private String nickname;
}
