package boi.projs.library.repository;

import boi.projs.library.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Author findByName(String name);

    List<Author> findByUserId(UUID id);

    List<Author> findByUserLogin(String login);

    @Query("select a from Author a join fetch a.books where a.id = ?1")
    List<Author> findByNameWithBooks();

}
