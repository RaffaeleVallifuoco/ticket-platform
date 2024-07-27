package it.raffo.my_dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.raffo.my_dashboard.model.Category;
import it.raffo.my_dashboard.model.Ticket;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
