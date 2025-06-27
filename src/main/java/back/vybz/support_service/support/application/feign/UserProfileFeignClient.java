package back.vybz.support_service.support.application.feign;

import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.support.vo.response.ResponseUserProfileVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-info-service", url = "http://localhost:8021")
public interface UserProfileFeignClient {

    @GetMapping("/user-info-service/api/v1/user/profile/{userUuid}")
    BaseResponseEntity<ResponseUserProfileVo> getUserProfile(@PathVariable String userUuid);
}
