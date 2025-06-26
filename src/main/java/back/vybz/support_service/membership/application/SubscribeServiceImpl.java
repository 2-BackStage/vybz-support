package back.vybz.support_service.membership.application;

import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.common.exception.BaseException;
import back.vybz.support_service.membership.application.feign.PaymentFeignClient;
import back.vybz.support_service.membership.dto.request.RequestSubscribeDto;
import back.vybz.support_service.membership.dto.request.RequestSubscriptionCancelDto;
import back.vybz.support_service.membership.dto.response.ResponseBillingKeyDto;
import back.vybz.support_service.membership.infrastructure.MemberShipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {

    private final PaymentFeignClient paymentFeignClient;

    private final MemberShipRepository memberShipRepository;

    @Transactional
    @Override
    public ResponseBillingKeyDto subscribe(RequestSubscribeDto requestSubscribeDto) {

        log.info("🔗BillingKey Feign 호출 시작");

        log.info(requestSubscribeDto.toString());

        if (memberShipRepository.existsByUserUuidAndBuskerUuidAndDeletedFalse(
                requestSubscribeDto.getUserUuid(), requestSubscribeDto.getBuskerUuid())) {
            throw new BaseException(BaseResponseStatus.ALREADY_SUBSCRIBED);
        }

        BaseResponseEntity<ResponseBillingKeyDto> response =
                paymentFeignClient.requestBillingKey(requestSubscribeDto);

        ResponseBillingKeyDto responseBillingKeyDto = response.result();

        log.info("✅ billingKey: {}", responseBillingKeyDto.getBillingKey());

        return responseBillingKeyDto;
    }

    @Transactional
    @Override
    public void cancelSubscription(RequestSubscriptionCancelDto requestSubscriptionCancelDto) {

        log.info("🔗 Subscription Cancel Feign 호출 시작");

        log.info(requestSubscriptionCancelDto.toString());

        BaseResponseEntity<Void> response =
                paymentFeignClient.subscriptionCancel(requestSubscriptionCancelDto);

        log.info("✅ Subscription Cancel 완료");
    }
}
