package repository;

import entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    
    List<Ticket> findByStatus(Ticket.Status status);

   
    List<Ticket> findByCategory(Ticket.Category category);

    
    List<Ticket> findByStatusAndCategory(Ticket.Status status, Ticket.Category category);

    
    @Query("""
        SELECT t FROM Ticket t
        WHERE LOWER(t.customerName) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(t.email)        LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(t.description)  LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY t.createdAt DESC
        """)
    List<Ticket> searchByKeyword(@Param("keyword") String keyword);

    List<Ticket> findAllByOrderByCreatedAtDesc();

    
    long countByStatus(Ticket.Status status);
}
