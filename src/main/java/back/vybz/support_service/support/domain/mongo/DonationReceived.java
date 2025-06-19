package back.vybz.support_service.support.domain.mongo;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@NoArgsConstructor
@Document(collection = "donation_received")
public class DonationReceived {

    @Id
    private String id;

    @Field("busker_uuid")
    private String buskerUuid;

    @Field("donation_user")
    private List<DonationUser> donationUser;

    @Field("total_received_amount")
    private Integer totalReceivedAmount;

    @Builder
    public DonationReceived(String id, String buskerUuid,
                            List<DonationUser> donationUser, Integer totalReceivedAmount) {
        this.id = id;
        this.buskerUuid = buskerUuid;
        this.donationUser = donationUser;
        this.totalReceivedAmount = totalReceivedAmount;
    }
}
