package it.raffo.my_dashboard.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.raffo.my_dashboard.model.Note;
import it.raffo.my_dashboard.model.Ticket;
import it.raffo.my_dashboard.model.User;
import it.raffo.my_dashboard.repository.NotesRepo;
import it.raffo.my_dashboard.repository.TicketRepo;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    NotesRepo noteRepo;

    @GetMapping("/admin")
    public String index(Model model, @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "body", required = false) String body) {

        List<Ticket> ticketList = new ArrayList<>();

        if (title == null && body == null) {

            ticketList = ticketRepo.findAll();

        } else if (title == null) {
            ticketList = ticketRepo.findByBodyContainingIgnoreCase(body);
        } else {

            ticketList = ticketRepo.findByTitleContainingIgnoreCase(title);
        }

        model.addAttribute("list", ticketList);

        return "/admin/home";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("ticket", ticketRepo.getReferenceById(id));

        return "/common/info";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ticket", ticketRepo.getReferenceById(id));
        model.addAttribute("status", Ticket.Status.values());
        return "/common/edit";
    }

    @PostMapping("/{id}/edit")
    public String Update(@PathVariable("id") Integer id, @Valid @ModelAttribute("ticket") Ticket ticketUpdate,
            BindingResult bindingresult,
            Model model) {

        if (bindingresult.hasErrors()) {

            return "/common/edit";
        }
        Ticket existingTicket = ticketRepo.getReferenceById(id);

        existingTicket.setTitle(ticketUpdate.getTitle());
        existingTicket.setBody(ticketUpdate.getBody());
        existingTicket.setStatus(ticketUpdate.getStatus());

        ticketRepo.save(existingTicket);

        return "redirect:/ticket/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        // TODO: process POST request

        ticketRepo.deleteById(id);

        return "redirect:/ticket/admin";
    }

    @GetMapping("/{id}/createNote")
    public String getCreateNote(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("ticketId", id);
        return "/common/createNote";
    }

    @PostMapping("/{id}/createNote")
    public String createNote(@PathVariable("id") Integer id, @Valid Note note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ticketId", id);
            return "/common/createNote";
        }

        Ticket ticket = ticketRepo.getReferenceById(id);
        note.setTicket(ticket);
        note.setNote_date(LocalDateTime.now());
        noteRepo.save(note);

        return "redirect:/ticket/" + id;
    }

}
