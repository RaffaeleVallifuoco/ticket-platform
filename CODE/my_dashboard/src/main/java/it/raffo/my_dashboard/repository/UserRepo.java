package it.raffo.my_dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.raffo.my_dashboard.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
