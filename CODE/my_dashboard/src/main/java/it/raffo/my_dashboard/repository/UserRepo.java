package it.raffo.my_dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.raffo.my_dashboard.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    // Metodo per trovare gli operatori disponibili
    List<User> findByStatusTrue();

    Optional<User> findById(Integer Id);

}
