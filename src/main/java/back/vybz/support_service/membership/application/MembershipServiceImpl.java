package back.vybz.support_service.membership.application;

import back.vybz.support_service.membership.domain.MemberShip;
import back.vybz.support_service.membership.domain.MemberShipStatus;
import back.vybz.support_service.membership.dto.response.ResponseMemberShipDto;
import back.vybz.support_service.membership.infrastructure.MemberShipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MemberShipRepository memberShipRepository;

    @Override
    public List<ResponseMemberShipDto> getActiveMemberships(String userUuid) {
        List<MemberShip> activeMemberships = memberShipRepository
                .findByUserUuidAndMemberShipStatusAndDeletedFalse(userUuid, MemberShipStatus.SUCCESS);

        return activeMemberships.stream()
                .map(ResponseMemberShipDto::from)
                .toList();
    }

    @Override
    public List<ResponseMemberShipDto> getExpiredMemberships(String userUuid) {
        List<MemberShip> expiredMemberships = memberShipRepository
                .findByUserUuidAndMemberShipStatusAndDeletedTrue(userUuid, MemberShipStatus.CANCELED);

        List<MemberShip> activeMemberships = memberShipRepository
                .findByUserUuidAndMemberShipStatusAndDeletedFalse(userUuid, MemberShipStatus.SUCCESS);

        Set<String> activeBuskerUuids = activeMemberships.stream()
                .map(MemberShip::getBuskerUuid)
                .collect(Collectors.toSet());

        return expiredMemberships.stream()
                .filter(m -> !activeBuskerUuids.contains(m.getBuskerUuid()))
                .map(ResponseMemberShipDto::from)
                .toList();

    }
}
