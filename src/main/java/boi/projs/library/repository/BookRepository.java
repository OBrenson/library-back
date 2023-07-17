package boi.projs.library.repository;

import boi.projs.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByAuthorName(String name);

    List<Book> findByAuthorId(UUID id);
}
