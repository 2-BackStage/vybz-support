package back.vybz.support_service.membership.application.feign;

import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.membership.dto.request.RequestSubscriptionStatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "live-service")
public interface LiveFeignClient {

    @PostMapping("/live-service/api/v1/membership/subscription-status")
    BaseResponseEntity<Void> sendSubscriptionStatus(@RequestBody RequestSubscriptionStatusDto requestSubscriptionStatusDto);
} 