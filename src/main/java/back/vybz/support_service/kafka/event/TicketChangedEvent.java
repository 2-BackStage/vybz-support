package back.vybz.support_service.kafka.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketChangedEvent {

    private String userUuid;

    private Integer ticketCount;

    @Builder
    public TicketChangedEvent(String userUuid, Integer ticketCount) {
        this.userUuid = userUuid;
        this.ticketCount = ticketCount;
    }
}
