package boi.projs.library.repository;

import boi.projs.library.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Author findByName(String name);

}
