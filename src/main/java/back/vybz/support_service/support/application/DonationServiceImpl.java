package back.vybz.support_service.support.application;

import back.vybz.support_service.common.dto.request.RequestPageDTO;
import back.vybz.support_service.common.dto.response.ResponsePageDTO;
import back.vybz.support_service.common.entity.BaseResponseStatus;
import back.vybz.support_service.common.exception.BaseException;
import back.vybz.support_service.support.application.feign.BuskerProfileFeignClient;
import back.vybz.support_service.support.application.feign.UserProfileFeignClient;
import back.vybz.support_service.support.domain.mongo.BuskerInfo;
import back.vybz.support_service.support.domain.mongo.Donation;
import back.vybz.support_service.support.domain.mongo.UserInfo;
import back.vybz.support_service.support.domain.mysql.DonationHistory;
import back.vybz.support_service.support.domain.mysql.DonationState;
import back.vybz.support_service.support.domain.mysql.DonationWallet;
import back.vybz.support_service.support.dto.request.RequestDonationDto;
import back.vybz.support_service.support.dto.response.ResponseDonationHistoryDto;
import back.vybz.support_service.support.dto.response.ResponseDonationReceivedHistoryDto;
import back.vybz.support_service.support.infrastructure.mongo.DonationMongoRepository;
import back.vybz.support_service.support.infrastructure.mysql.DonationHistoryRepository;
import back.vybz.support_service.support.infrastructure.mysql.DonationWalletRepository;
import back.vybz.support_service.support.vo.response.ResponseBuskerProfileVo;
import back.vybz.support_service.support.vo.response.ResponseUserProfileVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationWalletRepository donationWalletRepository;

    private final DonationHistoryRepository donationHistoryRepository;

    private final DonationMongoRepository donationMongoRepository;

    private final BuskerProfileFeignClient buskerProfileFeignClient;

    private final UserProfileFeignClient userProfileFeignClient;

    private static final int TICKET_UNIT_PRICE = 110;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Transactional
    @Override
    public void useDonateVTicket(RequestDonationDto requestDonationDto) {

        DonationWallet donationWallet = donationWalletRepository.findByUserUuid(requestDonationDto.getUserUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.DONATION_WALLET_NOT_FOUND));

        donationWallet.useTickets(requestDonationDto.getTicketAmount());

        // donationWalletRepository.save(donationWallet);

        DonationHistory donation = DonationHistory.builder()
                .donationReceivedUuid(UUID.randomUUID().toString())
                .userUuid(requestDonationDto.getUserUuid())
                .buskerUuid(requestDonationDto.getBuskerUuid())
                .ticketAmount(requestDonationDto.getTicketAmount())
                .amount(requestDonationDto.getTicketAmount() * TICKET_UNIT_PRICE)
                .donationState(DonationState.DONATION)
                .message(requestDonationDto.getMessage())
                .build();

        log.info("✅ 후원 후 남은 티켓 수: {}", donationWallet.getTicketCount());

        ResponseBuskerProfileVo responseBuskerProfileVo = Optional.ofNullable(
                buskerProfileFeignClient.getBuskerProfile(requestDonationDto.getBuskerUuid()).result()
        ).orElseThrow(() -> new BaseException(BaseResponseStatus.BUSKER_INFO_NOT_FOUND));

        ResponseUserProfileVo responseUserProfileVo = Optional.ofNullable(
                userProfileFeignClient.getUserProfile(requestDonationDto.getUserUuid()).result()
        ).orElseThrow(() -> new BaseException(BaseResponseStatus.USER_INFO_NOT_FOUND));

        BuskerInfo buskerInfo = BuskerInfo.builder()
                .buskerNickname(responseBuskerProfileVo.getNickname())
                .buskerProfileImageUrl(responseBuskerProfileVo.getProfileImageUrl())
                .build();

        UserInfo userInfo = UserInfo.builder()
                .userNickname(responseUserProfileVo.getNickname())
                .userProfileImageUrl(responseUserProfileVo.getProfileImageUrl())
                .build();

        try {
            donationMongoRepository.save(
                    back.vybz.support_service.support.domain.mongo.Donation.builder()
                            .userUuid(donation.getUserUuid())
                            .buskerUuid(donation.getBuskerUuid())
                            .ticketCount(donation.getTicketAmount())
                            .message(donation.getMessage())
                            .user(userInfo)
                            .busker(buskerInfo)
                            .build()
            );
        } catch (Exception e) {
            log.warn("⚠️ Mongo 저장 실패 - 무시하고 진행: {}", e.getMessage()); // 추후 수정 예정
        }

        donationHistoryRepository.save(donation);
    }

    /**
     * 사용자 후원한 내역 조회
     *
     * @param userUuid
     * @param pageRequestDTO
     * @return
     */
    @Override
    public ResponsePageDTO<ResponseDonationHistoryDto> getDonationHistoryByUser(String userUuid, RequestPageDTO pageRequestDTO) {

        if (!donationMongoRepository.existsByUserUuid(userUuid)) {
            throw new BaseException(BaseResponseStatus.NO_DONATION_RECORD);
        }

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by(Sort.Direction.DESC, "donatedAt")
        );

        Page<Donation> pageResult = donationMongoRepository.findByUserUuid(
                userUuid, pageable
        );

        List<ResponseDonationHistoryDto> dtoList = pageResult.getContent().stream()
                .map(donation -> ResponseDonationHistoryDto.builder()
                        .buskerUuid(donation.getBuskerUuid())
                        .nickname(donation.getBusker().getBuskerNickname())
                        .profileImageUrl(donation.getBusker().getBuskerProfileImageUrl())
                        .ticketCount(donation.getTicketCount())
                        .message(donation.getMessage())
                        .donatedAt(DATE_FORMATTER.format(
                                donation.getDonatedAt().atZone(ZoneId.of("Asia/Seoul")).toLocalDate())
                        )
                        .build()
                ).toList();

        return ResponsePageDTO.<ResponseDonationHistoryDto>builder()
                .type("DONATION")
                .dtoList(dtoList)
                .requestPageDTO(pageRequestDTO)
                .totalCount(pageResult.getTotalElements())
                .build();
    }

    /**
     * 버스커 후원 받은 내역 조회
     *
     * @param buskerUuid
     * @param pageRequestDTO
     * @return
     */
    @Override
    public ResponsePageDTO<ResponseDonationReceivedHistoryDto> getReceivedDonationHistoryByUser(String buskerUuid, RequestPageDTO pageRequestDTO) {

        if (!donationMongoRepository.existsByBuskerUuid(buskerUuid)) {
            throw new BaseException(BaseResponseStatus.NO_DONATION_RECEIVED);
        }

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by(Sort.Direction.DESC, "donatedAt")
        );

        Page<Donation> pageResult = donationMongoRepository.findByBuskerUuid(
                buskerUuid, pageable
        );

        List<ResponseDonationReceivedHistoryDto> donationReceivedHistoryDtoList = pageResult.getContent().stream()
                .map(donation -> ResponseDonationReceivedHistoryDto.builder()
                        .userUuid(donation.getUserUuid())
                        .nickname(donation.getUser().getUserNickname())
                        .profileImageUrl(donation.getUser().getUserProfileImageUrl())
                        .ticketCount(donation.getTicketCount())
                        .message(donation.getMessage())
                        .receivedAt(DATE_FORMATTER.format(
                                donation.getDonatedAt().atZone(ZoneId.of("Asia/Seoul")).toLocalDate())
                        )
                        .build()
                ).toList();

        return ResponsePageDTO.<ResponseDonationReceivedHistoryDto>builder()
                .type("RECEIVED_DONATION")
                .dtoList(donationReceivedHistoryDtoList)
                .requestPageDTO(pageRequestDTO)
                .totalCount(pageResult.getTotalElements())
                .build();
    }
}
