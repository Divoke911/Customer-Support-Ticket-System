package dto;

import entity.Ticket;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTicketRequest {

    @NotBlank(message = "Customer name is required")
    @Size(max = 255, message = "Customer name must not exceed 255 characters")
    private String customerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @NotNull(message = "Category is required")
    private Ticket.Category category;

    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;
}
