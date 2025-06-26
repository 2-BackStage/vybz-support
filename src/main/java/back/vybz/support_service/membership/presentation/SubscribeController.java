package back.vybz.support_service.membership.presentation;

import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.membership.application.SubscribeService;
import back.vybz.support_service.membership.dto.request.RequestSubscribeDto;
import back.vybz.support_service.membership.dto.request.RequestSubscriptionCancelDto;
import back.vybz.support_service.membership.dto.response.ResponseBillingKeyDto;
import back.vybz.support_service.membership.vo.request.RequestSubscribeVo;
import back.vybz.support_service.membership.vo.request.RequestSubscriptionCancelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscription")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/save-billing-key")
    public BaseResponseEntity<ResponseBillingKeyDto> subscribe(@RequestBody RequestSubscribeVo requestSubscribeVo) {
        log.info("🔔 정기 결제 요청: {}", requestSubscribeVo);

        ResponseBillingKeyDto responseBillingKeyDto = subscribeService.subscribe(RequestSubscribeDto.from(requestSubscribeVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS, responseBillingKeyDto);
    }


    @DeleteMapping
    public BaseResponseEntity<Void> subscribeCancel(
            @RequestBody RequestSubscriptionCancelVo requestSubscriptionCancelVo
            ) {

        log.info("🔔구독 해지 요청: {}", requestSubscriptionCancelVo);

        subscribeService.cancelSubscription(RequestSubscriptionCancelDto.from(requestSubscriptionCancelVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

}
