package back.vybz.support_service.support.presentation;

import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.support.application.DonationService;
import back.vybz.support_service.support.dto.request.RequestDonationDto;
import back.vybz.support_service.support.vo.request.RequestDonationVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/donation")
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    public BaseResponseEntity<Void> sendDonation(
            @RequestBody RequestDonationVo requestDonationVo
    ) {

        donationService.useDonateVTicket(RequestDonationDto.from(requestDonationVo));

        return new BaseResponseEntity<>(BaseResponseStatus.DONATION_SUCCESS);
    }
}
