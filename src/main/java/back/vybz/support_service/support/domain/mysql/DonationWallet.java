package back.vybz.support_service.support.domain.mysql;

import back.vybz.support_service.common.entity.BaseEntity;
import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.common.exception.BaseException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "donation_wallet")
@Getter
@NoArgsConstructor
public class DonationWallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uuid", nullable = false, unique = true)
    private String userUuid;

    @Column(name = "v_ticket_count", nullable = false)
    private Integer ticketCount;

    @Builder
    public DonationWallet(Long id, String userUuid, Integer ticketCount) {
        this.id = id;
        this.userUuid = userUuid;
        this.ticketCount = ticketCount;
    }

    public void addTickets(int amount) {
        this.ticketCount += amount;
    }

    public void useTickets(int amount) {
        if (this.ticketCount < amount) {
            throw new BaseException(BaseResponseStatus.INSUFFICIENT_V_TICKET);
        }
        this.ticketCount -= amount;
    }

    public void ticketsByRefund(int amount) {
        this.ticketCount -= amount;
    }
}
