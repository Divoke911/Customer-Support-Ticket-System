package service;

import dto.CreateTicketRequest;
import dto.TicketResponse;
import dto.UpdateTicketStatusRequest;
import entity.Ticket;
import repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional
    public TicketResponse createTicket(CreateTicketRequest request) {
        Ticket ticket = Ticket.builder()
                .customerName(request.getCustomerName().trim())
                .email(request.getEmail().trim().toLowerCase())
                .category(request.getCategory())
                .description(request.getDescription().trim())
                .status(Ticket.Status.Open)
                .build();

        Ticket saved = ticketRepository.save(ticket);
        return TicketResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> getAllTickets(
            Ticket.Status status,
            Ticket.Category category,
            String search) {

        List<Ticket> tickets;

        if (search != null && !search.isBlank()) {
            tickets = ticketRepository.searchByKeyword(search.trim());
        } else if (status != null && category != null) {
            tickets = ticketRepository.findByStatusAndCategory(status, category);
        } else if (status != null) {
            tickets = ticketRepository.findByStatus(status);
        } else if (category != null) {
            tickets = ticketRepository.findByCategory(category);
        } else {
            tickets = ticketRepository.findAllByOrderByCreatedAtDesc();
        }

        return tickets.stream().map(TicketResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        return TicketResponse.from(ticket);
    }

    @Transactional
    public TicketResponse updateTicketStatus(Long id, UpdateTicketStatusRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        ticket.setStatus(request.getStatus());
        Ticket updated = ticketRepository.save(ticket);
        return TicketResponse.from(updated);
    }

    @Transactional
    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        ticketRepository.delete(ticket);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getStatusSummary() {
        return Map.of(
                "open",       ticketRepository.countByStatus(Ticket.Status.Open),
                "inProgress", ticketRepository.countByStatus(Ticket.Status.In_Progress),
                "resolved",   ticketRepository.countByStatus(Ticket.Status.Resolved),
                "total",      ticketRepository.count()
        );
    }
}