package back.vybz.support_service.membership.domain;

import back.vybz.support_service.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_ship")
@Getter
@NoArgsConstructor
public class MemberShip extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uuid", nullable = false)
    private String userUuid;

    @Column(name = "busker_uuid", nullable = false)
    private String buskerUuid;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "status", nullable = false)
    private MemberShipStatus memberShipStatus;

    @Builder
    public MemberShip(Long id, String userUuid, String buskerUuid,
                      Integer price, MemberShipStatus memberShipStatus) {
        this.id = id;
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.price = price;
        this.memberShipStatus = memberShipStatus;
    }
}
