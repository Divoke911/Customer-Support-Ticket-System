package dto;

import entity.Ticket;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TicketResponse {

    private Long id;
    private String customerName;
    private String email;
    private String category;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TicketResponse from(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .customerName(ticket.getCustomerName())
                .email(ticket.getEmail())
                .category(ticket.getCategory().name())
                .description(ticket.getDescription())
                .status(ticket.getStatus().toValue())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }
}
