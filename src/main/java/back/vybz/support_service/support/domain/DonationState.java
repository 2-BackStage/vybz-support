package back.vybz.support_service.support.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DonationState {

    CHARGE("v-티켓 충전"),
    DONATION("후원");

    private final String donationState;
}
