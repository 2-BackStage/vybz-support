package back.vybz.support_service.settlement.application;

import back.vybz.support_service.common.dto.request.RequestPageDTO;
import back.vybz.support_service.common.dto.response.ResponsePageDTO;
import back.vybz.support_service.settlement.dto.request.RequestSettlementDto;
import back.vybz.support_service.settlement.dto.request.RequestSettlementUpdateDto;
import back.vybz.support_service.settlement.dto.response.ResponseSettlementHistoryDto;
import back.vybz.support_service.settlement.dto.response.ResponseSettlementStatusDto;

public interface SettlementService {

    // 정산 가능 금액 조회
    ResponseSettlementStatusDto getSettlementStatus(String buskerUuid);

    // 정산 신청
    void createSettlement(String buskerUuid, RequestSettlementDto requestSettlementDto);

    // 정산 신청 내역 조회
    ResponsePageDTO<ResponseSettlementHistoryDto> getSettlementHistory(String buskerUuid, RequestPageDTO requestPageDTO);

    // 정산 신청 수정
    void updateSettlement(String settlementsUuid, RequestSettlementUpdateDto requestSettlementUpdateDto);

}
