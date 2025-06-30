package back.vybz.support_service.settlement.application;

import back.vybz.support_service.common.dto.request.RequestPageDTO;
import back.vybz.support_service.common.dto.response.ResponsePageDTO;
import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.common.exception.BaseException;
import back.vybz.support_service.settlement.domain.Settlement;
import back.vybz.support_service.settlement.domain.SettlementStatus;
import back.vybz.support_service.settlement.dto.request.RequestSettlementDto;
import back.vybz.support_service.settlement.dto.request.RequestSettlementUpdateDto;
import back.vybz.support_service.settlement.dto.response.ResponseSettlementDto;
import back.vybz.support_service.settlement.dto.response.ResponseSettlementHistoryDto;
import back.vybz.support_service.settlement.dto.response.ResponseSettlementStatusDto;
import back.vybz.support_service.settlement.infrastructure.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SettlementServiceImpl implements SettlementService {

    private final SettlementRepository settlementRepository;

    private final IncomeService incomeService;

    private static final int MINIMUM_SETTLEMENT_AMOUNT = 5000;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    @Transactional(readOnly = true)
    public ResponseSettlementStatusDto getSettlementStatus(String buskerUuid) {
        // 총 수입 계산
        int totalIncome = incomeService.calculateTotalIncome(buskerUuid);

        // 대기중인 정산 금액
        int pendingAmount = settlementRepository.sumAmountByBuskerUuidAndStatus(buskerUuid, SettlementStatus.PENDING);

        // 완료된 정산 금액
        int completedAmount = settlementRepository.sumAmountByBuskerUuidAndStatus(buskerUuid, SettlementStatus.COMPLETED);

        // 정산 가능 금액 = 총 수입 - 대기중인 정산 - 완료된 정산
        int availableAmount = totalIncome - pendingAmount - completedAmount;

        return ResponseSettlementStatusDto.builder()
                .buskerUuid(buskerUuid)
                .totalIncome(totalIncome)
                .availableAmount(availableAmount)
                .pendingAmount(pendingAmount)
                .completedAmount(completedAmount)
                .build();
    }

    @Override
    public void createSettlement(String buskerUuid, RequestSettlementDto requestSettlementDto) {
        // 정산 가능 금액 확인
        ResponseSettlementStatusDto status = getSettlementStatus(buskerUuid);

        if (requestSettlementDto.getAmount() > status.getAvailableAmount()) {
            throw new BaseException(BaseResponseStatus.SETTLEMENT_AMOUNT_EXCEEDED);
        }

        if (requestSettlementDto.getAmount() < MINIMUM_SETTLEMENT_AMOUNT) {
            throw new BaseException(BaseResponseStatus.SETTLEMENT_INVALID_AMOUNT);
        }

        validateAccountNumber(requestSettlementDto.getAccountNumber());

        // 정산 엔티티 생성
        Settlement settlement = Settlement.builder()
                .settlementsUuid(UUID.randomUUID().toString())
                .buskerUuid(buskerUuid)
                .depositName(requestSettlementDto.getDepositName())
                .bankName(requestSettlementDto.getBankName())
                .accountNumber(requestSettlementDto.getAccountNumber())
                .amount(requestSettlementDto.getAmount())
                .settlementStatus(SettlementStatus.PENDING)
                .build();

        Settlement savedSettlement = settlementRepository.save(settlement);

        ResponseSettlementDto.builder()
                .settlementsUuid(savedSettlement.getSettlementsUuid())
                .buskerUuid(savedSettlement.getBuskerUuid())
                .amount(savedSettlement.getAmount())
                .status(savedSettlement.getSettlementStatus())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsePageDTO<ResponseSettlementHistoryDto> getSettlementHistory(String buskerUuid, RequestPageDTO requestPageDTO) {

        Pageable pageable = PageRequest.of(
                requestPageDTO.getPage() - 1,
                requestPageDTO.getSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<Settlement> settlementPage = settlementRepository.findByBuskerUuidOrderByCreatedAtDesc(
                buskerUuid, pageable);

        List<ResponseSettlementHistoryDto> dtoList = settlementPage.getContent().stream()
                .map(settlement -> ResponseSettlementHistoryDto.builder()
                        .settlementsUuid(settlement.getSettlementsUuid())
                        .buskerUuid(settlement.getBuskerUuid())
                        .depositName(settlement.getDepositName())
                        .bankName(settlement.getBankName())
                        .accountNumber(settlement.getAccountNumber())
                        .amount(settlement.getAmount())
                        .status(settlement.getSettlementStatus())
                        .rejectReason(settlement.getRejectReason())
                        .createdAt(DATE_FORMATTER.format(
                                settlement.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toLocalDate()))
                        .updatedAt(DATE_FORMATTER.format(
                                settlement.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")).toLocalDate()))
                        .build())
                .toList();

        return ResponsePageDTO.<ResponseSettlementHistoryDto>builder()
                .type("SETTLEMENT")
                .dtoList(dtoList)
                .requestPageDTO(requestPageDTO)
                .totalCount(settlementPage.getTotalElements())
                .build();
    }

    @Override
    public void updateSettlement(String settlementsUuid, RequestSettlementUpdateDto requestSettlementUpdateDto) {
        Settlement settlement = settlementRepository.findBySettlementsUuid(settlementsUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.SETTLEMENT_NOT_FOUND));

        // PENDING 상태인 경우에만 수정 가능
        if (settlement.getSettlementStatus() != SettlementStatus.PENDING) {
            throw new BaseException(BaseResponseStatus.SETTLEMENT_NOT_PENDING);
        }

        // 금액이 변경되는 경우에만 검증
        if (!settlement.getAmount().equals(requestSettlementUpdateDto.getAmount())) {
            // 현재 정산 가능 금액 조회
            ResponseSettlementStatusDto status = getSettlementStatus(settlement.getBuskerUuid());
            
            // 기존 정산 금액을 제외한 정산 가능 금액
            int availableAmountForUpdate = status.getAvailableAmount() + settlement.getAmount();
            
            // 새로운 금액이 정산 가능 금액을 초과하는지 확인
            if (requestSettlementUpdateDto.getAmount() > availableAmountForUpdate) {
                throw new BaseException(BaseResponseStatus.SETTLEMENT_UPDATE_AMOUNT_EXCEEDED);
            }
        }

        // 정산 정보 업데이트 - 기존 엔티티의 필드를 직접 수정
        settlement.updateSettlementInfo(
                requestSettlementUpdateDto.getDepositName(),
                requestSettlementUpdateDto.getBankName(),
                requestSettlementUpdateDto.getAccountNumber(),
                requestSettlementUpdateDto.getAmount()
        );

        Settlement updatedSettlement = settlementRepository.save(settlement);

        ResponseSettlementDto.builder()
                .settlementsUuid(updatedSettlement.getSettlementsUuid())
                .buskerUuid(updatedSettlement.getBuskerUuid())
                .amount(updatedSettlement.getAmount())
                .status(updatedSettlement.getSettlementStatus())
                .build();
    }

    private ResponseSettlementHistoryDto convertToHistoryDto(Settlement settlement) {
        return ResponseSettlementHistoryDto.builder()
                .settlementsUuid(settlement.getSettlementsUuid())
                .buskerUuid(settlement.getBuskerUuid())
                .depositName(settlement.getDepositName())
                .bankName(settlement.getBankName())
                .accountNumber(settlement.getAccountNumber())
                .amount(settlement.getAmount())
                .status(settlement.getSettlementStatus())
                .rejectReason(settlement.getRejectReason())
                .createdAt(String.valueOf(settlement.getCreatedAt()))
                .updatedAt(String.valueOf(settlement.getUpdatedAt()))
                .build();
    }

    private void validateAccountNumber(String accountNumber) {
        if (!accountNumber.matches("^\\d{10,14}$")) {
            throw new BaseException(BaseResponseStatus.SETTLEMENT_INVALID_ACCOUNT_NUMBER);
        }
    }

}
