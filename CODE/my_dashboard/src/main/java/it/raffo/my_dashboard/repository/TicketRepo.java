package it.raffo.my_dashboard.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import it.raffo.my_dashboard.model.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {

    public List<Ticket> findByTitleContainingIgnoreCase(String title);

    public List<Ticket> findByBodyContainingIgnoreCase(String body);

}
