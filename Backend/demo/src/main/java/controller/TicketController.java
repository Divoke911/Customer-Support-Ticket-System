package controller;

import dto.ApiResponse;
import dto.CreateTicketRequest;
import dto.TicketResponse;
import dto.UpdateTicketStatusRequest;
import entity.Ticket;
import service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {

    private final TicketService ticketService;

   
    @PostMapping
    public ResponseEntity<ApiResponse<TicketResponse>> createTicket(
            @Valid @RequestBody CreateTicketRequest request) {

        log.info("POST /api/tickets - creating ticket for {}", request.getEmail());
        TicketResponse ticket = ticketService.createTicket(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Ticket created successfully", ticket));
    }

   
    @GetMapping
    public ResponseEntity<ApiResponse<List<TicketResponse>>> getAllTickets(
            @RequestParam(required = false) Ticket.Status status,
            @RequestParam(required = false) Ticket.Category category,
            @RequestParam(required = false) String search) {

        log.info("GET /api/tickets - status={}, category={}, search={}", status, category, search);
        List<TicketResponse> tickets = ticketService.getAllTickets(status, category, search);
        return ResponseEntity.ok(
                ApiResponse.success("Tickets fetched successfully", tickets, tickets.size()));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponse>> getTicketById(@PathVariable Long id) {
        log.info("GET /api/tickets/{}", id);
        TicketResponse ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ApiResponse.success("Ticket fetched successfully", ticket));
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponse>> updateTicketStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTicketStatusRequest request) {

        log.info("PUT /api/tickets/{} - new status: {}", id, request.getStatus());
        TicketResponse updated = ticketService.updateTicketStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Ticket status updated successfully", updated));
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTicket(@PathVariable Long id) {
        log.info("DELETE /api/tickets/{}", id);
        ticketService.deleteTicket(id);
        return ResponseEntity.ok(ApiResponse.success("Ticket deleted successfully", null));
    }

   
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getSummary() {
        Map<String, Long> summary = ticketService.getStatusSummary();
        return ResponseEntity.ok(ApiResponse.success("Summary fetched", summary));
    }
}
