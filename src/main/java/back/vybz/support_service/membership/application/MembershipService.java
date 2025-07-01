package back.vybz.support_service.membership.application;

import back.vybz.support_service.membership.dto.response.ResponseMemberShipDto;
import back.vybz.support_service.membership.dto.response.ResponseSubscriptionCountDto;
import back.vybz.support_service.membership.dto.response.ResponseUserSubscriptionCountDto;

import java.util.List;

public interface MembershipService {

    List<ResponseMemberShipDto> getActiveMemberships(String userUuid);

    List<ResponseMemberShipDto> getExpiredMemberships(String userUuid);

    ResponseSubscriptionCountDto getSubscriptionCount(String buskerUuid);

    ResponseUserSubscriptionCountDto getUserSubscriptionCount(String userUuid);
}
