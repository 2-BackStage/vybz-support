package back.vybz.support_service.membership.application;

import back.vybz.support_service.membership.dto.request.RequestSubscribeDto;
import back.vybz.support_service.membership.dto.request.RequestSubscriptionCancelDto;
import back.vybz.support_service.membership.dto.response.ResponseBillingKeyDto;

public interface SubscribeService {

    ResponseBillingKeyDto subscribe(RequestSubscribeDto requestSubscribeDto);

    void cancelSubscription(RequestSubscriptionCancelDto requestSubscriptionCancelDto);
}
