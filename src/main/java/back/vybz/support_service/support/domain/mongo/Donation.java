package back.vybz.support_service.support.domain.mongo;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Document(collection = "donation")
public class Donation {

    @Id
    private String id;

    @Field(name = "user_uuid")
    private String userUuid;

    @Field(name = "busker_uuid")
    private String buskerUuid;

    @Field("user")
    private UserInfo user;

    @Field("busker")
    private BuskerInfo busker;

    @Field(name = "v_ticket_count")
    private Integer ticketCount;

    @Field(name = "message")
    private String message;

    @CreatedDate
    @Field(name = "donated_at")
    private Instant donatedAt;

    @Builder
    public Donation(String id, String userUuid, String buskerUuid,
                    UserInfo user, BuskerInfo busker, Integer ticketCount,
                    String message, Instant donatedAt) {
        this.id = id;
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.user = user;
        this.busker = busker;
        this.ticketCount = ticketCount;
        this.message = message;
        this.donatedAt = donatedAt;
    }
}
