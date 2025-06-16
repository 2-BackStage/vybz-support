package back.vybz.support_service.support.application;

import back.vybz.support_service.support.dto.request.RequestDonationDto;

public interface DonationService {

    void useDonateVTicket(RequestDonationDto requestDonationDto);
}
