package back.vybz.support_service.membership.application;

import back.vybz.support_service.membership.dto.response.ResponseMemberShipDto;

import java.util.List;

public interface MembershipService {

    List<ResponseMemberShipDto> getActiveMemberships(String userUuid);

    List<ResponseMemberShipDto> getExpiredMemberships(String userUuid);
}
