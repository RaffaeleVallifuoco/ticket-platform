package it.raffo.my_dashboard.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.raffo.my_dashboard.model.Note;
import it.raffo.my_dashboard.model.Role;
import it.raffo.my_dashboard.model.Ticket;
import it.raffo.my_dashboard.model.User;
import it.raffo.my_dashboard.repository.CategoryRepo;
import it.raffo.my_dashboard.repository.NotesRepo;
import it.raffo.my_dashboard.repository.TicketRepo;
import it.raffo.my_dashboard.repository.UserRepo;
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

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @GetMapping("/admin")
    public String index(Model model, @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "body", required = false) String body) {

        List<Ticket> ticketList = new ArrayList<>();

        if (title == null && body == null) {

            ticketList = ticketRepo.findAll();
            ticketList.sort(Comparator.comparing(Ticket::getTicket_date).reversed());

        } else if (title == null) {
            ticketList = ticketRepo.findByBodyContainingIgnoreCase(body);
            ticketList.sort(Comparator.comparing(Ticket::getTicket_date).reversed());
        } else {

            ticketList = ticketRepo.findByTitleContainingIgnoreCase(title);
            ticketList.sort(Comparator.comparing(Ticket::getTicket_date).reversed());
        }

        model.addAttribute("list", ticketList);

        int countInCorso = ticketRepo.countByStatus(Ticket.Status.IN_CORSO);
        int countDaFare = ticketRepo.countByStatus(Ticket.Status.DA_FARE);
        int countCompletato = ticketRepo.countByStatus(Ticket.Status.COMPLETATO);

        model.addAttribute("dafare", countDaFare);
        model.addAttribute("incorso", countInCorso);
        model.addAttribute("completato", countCompletato);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUser = userRepo.findByUsername(username);
        User user = currentUser.get();
        model.addAttribute("user", user);
        Set<Role> roles = user.getRoles();
        model.addAttribute("roles", roles);

        int countAvailable = userRepo.countByStatusTrue();
        model.addAttribute("available", countAvailable);

        return "/admin/home_admin";
    }

    @GetMapping("/user")
    public String getMyTickets(Model model, @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "body", required = false) String body) {

        List<Ticket> ticketList = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (title == null && body == null) {

            ticketList = ticketRepo.findByUserUsername(username);
            ticketList.sort(Comparator.comparing(Ticket::getTicket_date).reversed());

        } else if (title == null) {
            ticketList = ticketRepo.findByUserUsernameAndBodyContainingIgnoreCase(username, body);
            ticketList.sort(Comparator.comparing(Ticket::getTicket_date).reversed());
        } else {

            ticketList = ticketRepo.findByUserUsernameAndTitleContainingIgnoreCase(username, title);
            ticketList.sort(Comparator.comparing(Ticket::getTicket_date).reversed());
        }
        model.addAttribute("list", ticketList);

        int countInCorso = ticketRepo.countByUserUsernameAndStatus(username, Ticket.Status.IN_CORSO);
        int countDaFare = ticketRepo.countByUserUsernameAndStatus(username, Ticket.Status.DA_FARE);
        int countCompletato = ticketRepo.countByUserUsernameAndStatus(username, Ticket.Status.COMPLETATO);

        model.addAttribute("dafare", countDaFare);
        model.addAttribute("incorso", countInCorso);
        model.addAttribute("completato", countCompletato);

        Optional<User> currentUser = userRepo.findByUsername(username);
        User user = currentUser.get();
        model.addAttribute("user", user);
        Set<Role> roles = user.getRoles();
        model.addAttribute("roles", roles);

        return "/user/home_user";
    }

    @GetMapping("/admin/create")
    public String create(Model model) {

        model.addAttribute("ticket", new Ticket());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("category", categoryRepo.findAll());
        List<User> availableUser = userRepo.findByStatusTrue();
        model.addAttribute("availableUser", availableUser);

        return "/admin/create";
    }

    @PostMapping("/admin/create")
    public String store(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingresult, Model model) {
        // TODO: process POST request

        if (bindingresult.hasErrors()) {
            model.addAttribute("category", categoryRepo.findAll());
            List<User> availableUser = userRepo.findByStatusTrue();
            model.addAttribute("availableUser", availableUser);

            return "/admin/create";
        }

        ticketForm.setTicket_date(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        ticketForm.setStatus(Ticket.Status.DA_FARE);

        ticketRepo.save(ticketForm);

        return "redirect:/ticket/admin";

    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Ticket ticket = ticketRepo.getReferenceById(id);

        List<Note> getNoteByDateDISC = ticket.getNote();
        getNoteByDateDISC.sort(Comparator.comparing(Note::getNoteDate).reversed());

        model.addAttribute("ticket", ticket);
        model.addAttribute("noteByDate", getNoteByDateDISC);

        model.addAttribute("note", new Note());
        model.addAttribute("ticketId", id);

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
            model.addAttribute("status", Ticket.Status.values());

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

    // @GetMapping("/{id}/createNote")
    // public String getCreateNote(@PathVariable("id") Integer id, Model model) {
    // model.addAttribute("note", new Note());
    // model.addAttribute("ticketId", id);
    // return "/common/createNote";
    // }

    // @PostMapping("/{id}/createNote")
    // public String createNote(@PathVariable("id") Integer id, @Valid Note note,
    // BindingResult bindingResult,
    // Model model) {
    // if (bindingResult.hasErrors()) {
    // model.addAttribute("ticketId", id);
    // return "/common/createNote";
    // }

    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // String username = authentication.getName();

    // Optional<User> user = userRepo.findByUsername(username);
    // User loggedUser = user.get();

    // Ticket ticket = ticketRepo.getReferenceById(id);
    // note.setTicket(ticket);
    // note.setId(null);
    // note.setNoteDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    // note.setAuthor(loggedUser);
    // noteRepo.save(note);

    // return "redirect:/ticket/" + id;
    // }

    @PostMapping("/{id}")
    public String createNote(@PathVariable("id") Integer id, @Valid Note note, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ticketId", id);
            return "/ticket/" + id;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> user = userRepo.findByUsername(username);
        User loggedUser = user.get();

        Ticket ticket = ticketRepo.getReferenceById(id);
        note.setTicket(ticket);
        note.setId(null);
        note.setNoteDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        note.setAuthor(loggedUser);
        noteRepo.save(note);

        return "redirect:/ticket/" + id;
    }

}
