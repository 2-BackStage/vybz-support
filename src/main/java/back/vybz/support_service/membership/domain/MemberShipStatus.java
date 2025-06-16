package back.vybz.support_service.membership.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberShipStatus {

    SUCCESS("멤버십 결제"),
    CANCELED("멤버십 결제 취소");

    private final String memberShipStatus;
}
