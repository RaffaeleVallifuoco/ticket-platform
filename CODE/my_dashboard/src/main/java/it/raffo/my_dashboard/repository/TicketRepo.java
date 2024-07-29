package it.raffo.my_dashboard.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.raffo.my_dashboard.model.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer> {

    public List<Ticket> findByTitleContainingIgnoreCase(String title);

    public List<Ticket> findByBodyContainingIgnoreCase(String body);

    List<Ticket> findByUserUsername(String username);

    List<Ticket> findByUserUsernameAndBodyContainingIgnoreCase(String username, String body);

    List<Ticket> findByUserUsernameAndTitleContainingIgnoreCase(String username, String title);

    @Query("SELECT t FROM Ticket t WHERE t.category.name = :categoryName")
    List<Ticket> findByCategoryName(@Param("categoryName") String categoryName);

    // Metodo per contare i ticket "da fare" o "in corso" di un operatore usando
    // metodo sql "IN" (incluso)
    int countByUserIdAndStatusIn(Integer userId, List<String> status);

    List<Ticket> findByStatus(Ticket.Status status);

}
