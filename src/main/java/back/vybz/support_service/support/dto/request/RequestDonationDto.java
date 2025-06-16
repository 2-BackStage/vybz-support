package back.vybz.support_service.support.dto.request;

import back.vybz.support_service.support.vo.request.RequestDonationVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestDonationDto {

    private String userUuid;

    private String buskerUuid;

    private Integer ticketAmount;

    private String message;

    @Builder
    public RequestDonationDto(String userUuid, String buskerUuid, int ticketAmount, String message) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.ticketAmount = ticketAmount;
        this.message = message;
    }

    public static RequestDonationDto from(RequestDonationVo requestDonationVo) {
        return RequestDonationDto.builder()
                .userUuid(requestDonationVo.getUserUuid())
                .buskerUuid(requestDonationVo.getBuskerUuid())
                .ticketAmount(requestDonationVo.getTicketAmount())
                .message(requestDonationVo.getMessage())
                .build();
    }
}
