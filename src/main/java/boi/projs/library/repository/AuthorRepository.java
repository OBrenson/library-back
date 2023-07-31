package boi.projs.library.repository;

import boi.projs.library.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Author findByNameAndUser_Id(String name, UUID id);

    List<Author> findByUserId(UUID id);

    List<Author> findByUserLogin(String login);

    @Query("select distinct a from Author a join fetch a.books where a.name = ?1")
    List<Author> findByNameWithBooks(String name);

}
