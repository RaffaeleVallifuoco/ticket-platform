package it.raffo.my_dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.raffo.my_dashboard.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
