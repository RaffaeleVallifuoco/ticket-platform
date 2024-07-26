package it.raffo.my_dashboard.model;

import java.time.LocalDateTime;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Campo Obbligatorio")
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @NotBlank(message = "Campo Obbligatorio")
    @Column(name = "body", length = 800, nullable = false)
    private String body;

    // @NotNull(message = "Campo Obbligatorio")
    @Column(name = "date", nullable = false)
    private LocalDateTime ticket_date;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        DA_FARE, IN_CORSO, COMPLETATO
    }

    // @NotNull(message = "Campo Obbligatorio")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // @NotNull(message = "Campo Obbligatorio")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Note> note;

    // -----------------------------------
    // -------- GETTERS & SETTERS --------
    // -----------------------------------

    public List<Note> getNote() {
        return note;
    }

    public void setNote(List<Note> note) {
        this.note = note;
    }

    public LocalDateTime getTicket_date() {
        return ticket_date;
    }

    public void setTicket_date(LocalDateTime ticket_date) {
        this.ticket_date = ticket_date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
