package back.vybz.support_service.support.domain.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class DonationUser {

    private String userUuid;

    private String userNickname;

    private Integer ticketCount;

    private String message;

    private Instant receivedAt;
}
