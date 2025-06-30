package back.vybz.support_service.settlement.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SettlementStatus {

    PENDING("정산 대기"),
    APPROVED("승인됨"),
    REJECTED("반려됨"),
    PROCESSING("처리중"),
    COMPLETED("완료"),
    FAILED("실패");

    private final String settlementStatus;
}
