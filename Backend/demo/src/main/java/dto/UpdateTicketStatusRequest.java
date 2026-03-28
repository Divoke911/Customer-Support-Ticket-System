package dto;

import entity.Ticket;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTicketStatusRequest {

    @NotNull(message = "Status is required")
    private Ticket.Status status;
}
