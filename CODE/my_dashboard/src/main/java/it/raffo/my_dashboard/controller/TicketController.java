package it.raffo.my_dashboard.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.raffo.my_dashboard.model.Ticket;
import it.raffo.my_dashboard.repository.TicketRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketRepo ticketRepo;

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
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ticket", ticketRepo.getReferenceById(id));
        model.addAttribute("status", Ticket.Status.values());
        return "/common/edit";
    }

    @PostMapping("/{id}/update")
    public String updateTicket(@PathVariable("id") Long id, @ModelAttribute("ticket") Ticket ticket) {
        ticketRepo.save(ticket);
        return "redirect:/tickets";
    }

}
