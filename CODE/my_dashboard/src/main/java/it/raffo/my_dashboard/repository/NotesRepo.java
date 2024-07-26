package it.raffo.my_dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.raffo.my_dashboard.model.Note;

public interface NotesRepo extends JpaRepository<Note, Integer> {

}
