package back.vybz.support_service.support.application;

import back.vybz.support_service.common.dto.request.RequestPageDTO;
import back.vybz.support_service.common.dto.response.ResponsePageDTO;
import back.vybz.support_service.support.dto.request.RequestDonationDto;
import back.vybz.support_service.support.dto.response.ResponseDonationHistoryDto;
import back.vybz.support_service.support.dto.response.ResponseDonationReceivedHistoryDto;

public interface DonationService {

    void useDonateVTicket(RequestDonationDto requestDonationDto);

    ResponsePageDTO<ResponseDonationHistoryDto> getDonationHistoryByUser(String userUuid, RequestPageDTO pageRequestDTO);

    ResponsePageDTO<ResponseDonationReceivedHistoryDto> getReceivedDonationHistoryByUser(String buskerUuid, RequestPageDTO pageRequestDTO);
}
