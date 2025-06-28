package back.vybz.support_service.membership.dto.response;

import back.vybz.support_service.membership.domain.MemberShip;
import back.vybz.support_service.membership.domain.MemberShipStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponseMemberShipDto {

    private String userUuid;

    private String buskerUuid;

    private Integer price;

    private MemberShipStatus memberShipStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public ResponseMemberShipDto(String userUuid, String buskerUuid, Integer price,
                                 MemberShipStatus memberShipStatus, LocalDateTime createdAt,
                                 LocalDateTime updatedAt) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.price = price;
        this.memberShipStatus = memberShipStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ResponseMemberShipDto from(MemberShip memberShip) {
        ResponseMemberShipDto dto = new ResponseMemberShipDto();
        dto.userUuid = memberShip.getUserUuid();
        dto.buskerUuid = memberShip.getBuskerUuid();
        dto.price = memberShip.getPrice();
        dto.memberShipStatus = memberShip.getMemberShipStatus();
        dto.createdAt = memberShip.getCreatedAt();
        dto.updatedAt = memberShip.getUpdatedAt();
        return dto;
    }
}
