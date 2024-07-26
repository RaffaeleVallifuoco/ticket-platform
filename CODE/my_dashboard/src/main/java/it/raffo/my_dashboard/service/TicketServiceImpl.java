package it.raffo.my_dashboard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.raffo.my_dashboard.model.Ticket;
import it.raffo.my_dashboard.repository.TicketRepo;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepo ticketRepo;

    @Override
    public List<Ticket> findAll() {
        // TODO Auto-generated method stub
        return ticketRepo.findAll();
    }

    @Override
    public Optional<Ticket> findbyId(Integer id) {
        // TODO Auto-generated method stub
        return ticketRepo.findById(id);
    }

}
