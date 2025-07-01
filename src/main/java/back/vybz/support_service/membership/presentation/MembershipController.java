package back.vybz.support_service.membership.presentation;

import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.membership.application.MembershipService;
import back.vybz.support_service.membership.dto.response.ResponseMemberShipDto;
import back.vybz.support_service.membership.dto.response.ResponseSubscriptionCountDto;
import back.vybz.support_service.membership.dto.response.ResponseUserSubscriptionCountDto;
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

    @Operation(summary = "버스커 구독자 수 조회", description = "특정 버스커의 활성 구독자 수를 조회합니다.")
    @GetMapping("/count/{buskerUuid}")
    public BaseResponseEntity<ResponseSubscriptionCountDto> getSubscriptionCount(@PathVariable String buskerUuid) {
        ResponseSubscriptionCountDto subscriptionCount = membershipService.getSubscriptionCount(buskerUuid);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS, subscriptionCount);
    }

    @Operation(summary = "유저 구독 개수 조회", description = "특정 유저가 구독한 버스커 수를 조회합니다.")
    @GetMapping("/user-count/{userUuid}")
    public BaseResponseEntity<ResponseUserSubscriptionCountDto> getUserSubscriptionCount(@PathVariable String userUuid) {
        ResponseUserSubscriptionCountDto userSubscriptionCount = membershipService.getUserSubscriptionCount(userUuid);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS, userSubscriptionCount);
    }
}
