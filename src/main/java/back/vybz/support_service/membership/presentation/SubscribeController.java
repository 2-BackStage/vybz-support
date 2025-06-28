package back.vybz.support_service.membership.presentation;

import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.membership.application.SubscribeService;
import back.vybz.support_service.membership.dto.request.RequestSubscribeDto;
import back.vybz.support_service.membership.dto.request.RequestSubscriptionCancelDto;
import back.vybz.support_service.membership.dto.response.ResponseBillingKeyDto;
import back.vybz.support_service.membership.vo.request.RequestSubscribeVo;
import back.vybz.support_service.membership.vo.request.RequestSubscriptionCancelVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscription")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @Operation(summary = "billingkey 발급을 위한 payment-service 호출 API", description = "billingkey 발급을 위한 payment-service 호출 API 입니다.", tags = {"Support-Subscription-Service"})
    @PostMapping("/save-billing-key")
    public BaseResponseEntity<ResponseBillingKeyDto> subscribe(@RequestBody RequestSubscribeVo requestSubscribeVo) {

        ResponseBillingKeyDto responseBillingKeyDto = subscribeService.subscribe(RequestSubscribeDto.from(requestSubscribeVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS, responseBillingKeyDto);
    }

    @Operation(summary = "자동결제를 위한 payment-service 호출 API", description = "자동결제를 위한 payment-service 호출 API 입니다.", tags = {"Support-Subscription-Service"})
    @DeleteMapping
    public BaseResponseEntity<Void> subscribeCancel(
            @RequestBody RequestSubscriptionCancelVo requestSubscriptionCancelVo
            ) {

        subscribeService.cancelSubscription(RequestSubscriptionCancelDto.from(requestSubscriptionCancelVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
