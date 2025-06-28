package back.vybz.support_service.membership.presentation;

import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.membership.application.MembershipService;
import back.vybz.support_service.membership.dto.response.ResponseMemberShipDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/membership")
public class MembershipController {

    private final MembershipService membershipService;

    @Operation(summary = "구독중인 멤버십 조회", description = "구독중인 멤버십 목록을 조회합니다.")
    @GetMapping("/active/{userUuid}")
    public BaseResponseEntity<List<ResponseMemberShipDto>> getActiveMemberships(@PathVariable String userUuid) {
        List<ResponseMemberShipDto> activeMemberships = membershipService.getActiveMemberships(userUuid);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS, activeMemberships);
    }

    @Operation(summary = "만료된 멤버십 조회", description = "만료된 멤버십 목록을 조회합니다. (동일 buskerUuid 다시 구독중이면 제외)")
    @GetMapping("/expired/{userUuid}")
    public BaseResponseEntity<List<ResponseMemberShipDto>> getExpiredMemberships(@PathVariable String userUuid) {
        List<ResponseMemberShipDto> expiredMemberships = membershipService.getExpiredMemberships(userUuid);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS, expiredMemberships);
    }
}
