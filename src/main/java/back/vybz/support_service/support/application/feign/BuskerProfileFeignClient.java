package back.vybz.support_service.support.application.feign;

import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.support.vo.response.ResponseBuskerProfileVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "busker-info-service")
public interface BuskerProfileFeignClient {

    @GetMapping("/api/v1/busker/profile/{buskerUuid}")
    BaseResponseEntity<ResponseBuskerProfileVo> getBuskerProfile(@PathVariable String buskerUuid);
}
