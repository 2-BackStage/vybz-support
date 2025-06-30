package back.vybz.support_service.settlement.presentation;

import back.vybz.support_service.common.dto.request.RequestPageDTO;
import back.vybz.support_service.common.dto.response.ResponsePageDTO;
import back.vybz.support_service.common.entity.BaseResponseEntity;
import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.settlement.application.SettlementService;
import back.vybz.support_service.settlement.dto.request.RequestSettlementDto;
import back.vybz.support_service.settlement.dto.request.RequestSettlementUpdateDto;
import back.vybz.support_service.settlement.dto.response.ResponseSettlementHistoryDto;
import back.vybz.support_service.settlement.dto.response.ResponseSettlementStatusDto;
import back.vybz.support_service.settlement.vo.request.RequestSettlementUpdateVo;
import back.vybz.support_service.settlement.vo.request.RequestSettlementVo;
import back.vybz.support_service.settlement.vo.response.ResponseSettlementHistoryVo;
import back.vybz.support_service.settlement.vo.response.ResponseSettlementVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    // 정산 가능 금액 조회
    @GetMapping("/status/{buskerUuid}")
    public BaseResponseEntity<ResponseSettlementStatusDto> getSettlementStatus(
            @PathVariable String buskerUuid
    ) {
        ResponseSettlementStatusDto responseSettlementStatusDto = settlementService.getSettlementStatus(buskerUuid);

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS, responseSettlementStatusDto);
    }

    // 정산 신청
    @PostMapping("/{buskerUuid}")
    public BaseResponseEntity<ResponseSettlementVo> createSettlement(
            @PathVariable String buskerUuid,
            @RequestBody RequestSettlementVo requestSettlementVo
    ) {

        settlementService.createSettlement(buskerUuid, RequestSettlementDto.fromSettlement(requestSettlementVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    // 정산 신청 내역 조회
    @GetMapping("/history/{buskerUuid}")
    public BaseResponseEntity<ResponsePageDTO<ResponseSettlementHistoryVo>> getSettlementHistory(
            @PathVariable String buskerUuid,
            @ModelAttribute RequestPageDTO requestPageDTO) {

        ResponsePageDTO<ResponseSettlementHistoryDto> responsePageDTO = settlementService.getSettlementHistory(buskerUuid, requestPageDTO);

        ResponsePageDTO<ResponseSettlementHistoryVo> result = ResponsePageDTO.<ResponseSettlementHistoryVo>builder()
                .type("PURCHASE")
                .dtoList(
                        responsePageDTO.getDtoList().stream()
                                .map(ResponseSettlementHistoryDto::toResponseSettlementHistoryVo)
                                .toList()
                )
                .requestPageDTO(responsePageDTO.getRequestPageDTO())
                .totalCount(responsePageDTO.getTotalCount())
                .build();

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS, result);
    }

    // 정산 신청 수정
    @PutMapping("/{settlementsUuid}")
    public BaseResponseEntity<ResponseSettlementVo> updateSettlement(
            @PathVariable String settlementsUuid,
            @RequestBody RequestSettlementUpdateVo requestSettlementUpdateVo) {

        settlementService.updateSettlement(settlementsUuid, RequestSettlementUpdateDto.from(requestSettlementUpdateVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

}
