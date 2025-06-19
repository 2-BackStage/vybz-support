package back.vybz.support_service.support.domain.mysql;

import back.vybz.support_service.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "donation_history")
@Getter
@NoArgsConstructor
public class DonationHistory extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "donation_received_uuid", unique = true)
    private String donationReceivedUuid;

    @Column(name = "user_uuid", nullable = false)
    private String userUuid;

    @Column(name = "busker_uuid")
    private String buskerUuid;

    @Column(name = "v_ticket_amount", nullable = false)
    private Integer ticketAmount;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "donation_status", nullable = false)
    private DonationState donationState;

    @Column(name = "message", length = 500)
    private String message;

    @Builder
    public DonationHistory(Long id, String donationReceivedUuid,
                           String userUuid, String buskerUuid, Integer ticketAmount, Integer amount,
                           DonationState donationState, String message) {
        this.id = id;
        this.donationReceivedUuid = donationReceivedUuid;
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.ticketAmount = ticketAmount;
        this.amount = amount;
        this.donationState = donationState;
        this.message = message;
    }
}
