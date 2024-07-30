package it.raffo.my_dashboard.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Campo Obbligatorio")
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @NotBlank(message = "Campo Obbligatorio")
    @Column(name = "username", length = 30, nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Campo Obbligatorio")
    @Column(name = "password", length = 30, nullable = false, unique = false)
    private String password;

    @Column(name = "status", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean status;
    // 0 = busy
    // 1 = available

    @NotBlank(message = "Campo Obbligatorio")
    @Column(name = "email", length = 60, nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ticket> ticket;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Role> roles;

    // -----------------------------------
    // -------- GETTERS & SETTERS --------
    // -----------------------------------

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

}
