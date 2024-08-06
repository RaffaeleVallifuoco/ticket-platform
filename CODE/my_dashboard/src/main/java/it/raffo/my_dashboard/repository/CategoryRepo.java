package it.raffo.my_dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.raffo.my_dashboard.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
