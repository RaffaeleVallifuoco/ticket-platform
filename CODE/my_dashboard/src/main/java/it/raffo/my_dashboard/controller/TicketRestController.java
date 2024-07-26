package it.raffo.my_dashboard.controller;

import it.raffo.my_dashboard.response.Payload;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.MultipartStream.IllegalBoundaryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.raffo.my_dashboard.model.Ticket;
import it.raffo.my_dashboard.response.Payload;
import it.raffo.my_dashboard.service.TicketService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin
@RequestMapping("/api/myDashboard")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/index")
    public ResponseEntity<Payload<List<Ticket>>> getAll() {
        List<Ticket> pizzaList = ticketService.findAll();
        return ResponseEntity.ok(new Payload<List<Ticket>>(pizzaList, null, HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payload<Ticket>> getId(@PathVariable("id") Integer ticketId) {

        Optional<Ticket> pizza = ticketService.findbyId(ticketId);

        if (pizza.isPresent()) {
            return ResponseEntity.ok(new Payload<Ticket>(pizza.get(), null, HttpStatus.OK));

        } else {
            return new ResponseEntity<Payload<Ticket>>(
                    new Payload<Ticket>(null, "Ticket con id " + ticketId + " non trovata", HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND);

        }

    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Payload<List<Ticket>>> getByCategoryName(@PathVariable("categoryName") String categoryName) {
        List<Ticket> tickets = ticketService.findByCategoryName(categoryName);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(
                    new Payload<>(null, "Nessun ticket trovato per la categoria " + categoryName, HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new Payload<>(tickets, null, HttpStatus.OK));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Payload<List<Ticket>>> getByStatus(@PathVariable("status") Ticket.Status status) {
        List<Ticket> tickets = ticketService.findByStatus(status);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(
                    new Payload<>(null, "Nessun ticket trovato per lo status " + status, HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new Payload<>(tickets, null, HttpStatus.OK));
    }
}
