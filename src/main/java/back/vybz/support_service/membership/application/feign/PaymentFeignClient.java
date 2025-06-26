package back.vybz.support_service.membership.application.feign;

import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.membership.dto.request.RequestSubscribeDto;
import back.vybz.support_service.membership.dto.request.RequestSubscriptionCancelDto;
import back.vybz.support_service.membership.dto.response.ResponseBillingKeyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:8087")
public interface PaymentFeignClient {

    @PostMapping("/payment-service/api/v1/membership/billing/register")
    BaseResponseEntity<ResponseBillingKeyDto> requestBillingKey(@RequestBody RequestSubscribeDto requestSubscribeDto);

    @DeleteMapping("/payment-service/api/v1/membership")
    BaseResponseEntity<Void> subscriptionCancel(@RequestBody RequestSubscriptionCancelDto requestSubscriptionCancelDto);
}
