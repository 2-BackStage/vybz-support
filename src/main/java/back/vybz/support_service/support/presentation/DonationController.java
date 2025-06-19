package back.vybz.support_service.support.presentation;

import back.vybz.support_service.common.dto.request.RequestPageDTO;
import back.vybz.support_service.common.dto.response.ResponsePageDTO;
import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.support.application.DonationService;
import back.vybz.support_service.support.dto.request.RequestDonationDto;
import back.vybz.support_service.support.dto.response.ResponseDonationHistoryDto;
import back.vybz.support_service.support.dto.response.ResponseDonationReceivedHistoryDto;
import back.vybz.support_service.support.vo.request.RequestDonationVo;
import back.vybz.support_service.support.vo.response.ResponseDonationHistoryVo;
import back.vybz.support_service.support.vo.response.ResponseDonationReceivedHistoryVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/donation")
public class DonationController {

    private final DonationService donationService;

    @Operation(summary = "후원 API", description = "후원 API 입니다.", tags = {"Support-Service"})
    @PostMapping
    public BaseResponseEntity<Void> sendDonation(
            @RequestBody RequestDonationVo requestDonationVo
    ) {

        donationService.useDonateVTicket(RequestDonationDto.from(requestDonationVo));

        return new BaseResponseEntity<>(BaseResponseStatus.DONATION_SUCCESS);
    }

    @Operation(summary = "사용자 - 후원 내역 조회 API", description = "후원 내역 조회 API 입니다.", tags = {"Support-Service"})
    @GetMapping("/user-history/{userUuid}")
    public BaseResponseEntity<ResponsePageDTO<ResponseDonationHistoryVo>> getDonationByUserId(
            @PathVariable String userUuid,
            @ModelAttribute RequestPageDTO requestPageDTO
    ) {

        ResponsePageDTO<ResponseDonationHistoryDto> responsePageDTO = donationService.getDonationHistoryByUser(userUuid, requestPageDTO);

        ResponsePageDTO<ResponseDonationHistoryVo> result = ResponsePageDTO.<ResponseDonationHistoryVo>builder()
                .type("PURCHASE")
                .dtoList(
                        responsePageDTO.getDtoList().stream()
                                .map(ResponseDonationHistoryDto::toResponseDonationHistoryVo)
                                .toList()
                )
                .requestPageDTO(responsePageDTO.getRequestPageDTO())
                .totalCount(responsePageDTO.getTotalCount())
                .build();

        log.info("result 확인 : {} ", responsePageDTO.getRequestPageDTO());

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS, result);
    }

    @Operation(summary = "버스커 - 후원받은 내역 조회 API", description = "후원받은 내역 조회 API 입니다.", tags = {"Support-Service"})
    @GetMapping("/busker-history/{buskerUuid}")
    public BaseResponseEntity<ResponsePageDTO<ResponseDonationReceivedHistoryVo>> getDonationHistoryByBuskerUuid(
            @PathVariable String buskerUuid,
            @ModelAttribute RequestPageDTO requestPageDTO
    ) {

        ResponsePageDTO<ResponseDonationReceivedHistoryDto> responsePageDTO = donationService.getReceivedDonationHistoryByUser(buskerUuid, requestPageDTO);

        ResponsePageDTO<ResponseDonationReceivedHistoryVo> result = ResponsePageDTO.<ResponseDonationReceivedHistoryVo>builder()
                .type("RECEIVED_DONATION")
                .dtoList(
                        responsePageDTO.getDtoList().stream()
                                .map(ResponseDonationReceivedHistoryDto::toResponseDonationReceivedHistoryVo)
                                .toList()
                )
                .requestPageDTO(responsePageDTO.getRequestPageDTO())
                .totalCount(responsePageDTO.getTotalCount())
                .build();

        log.info("result 확인 2: {} ", responsePageDTO.getRequestPageDTO());

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS, result);
    }
}
