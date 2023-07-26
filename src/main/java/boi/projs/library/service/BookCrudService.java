package boi.projs.library.service;

import boi.projs.library.domain.Book;
import boi.projs.library.logging.LoggableCrud;
import boi.projs.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@LoggableCrud
public class BookCrudService extends CrudService<Book> {

    @Autowired
    public BookCrudService(BookRepository bookRepository) {
        super(bookRepository);
    }

    public List<Book> findByAuthorName(String name) {
        return ((BookRepository)repository).findByAuthorName(name);
    }

    public List<Book> findByAuthorId(UUID id) {
        return ((BookRepository)repository).findByAuthorId(id);
    }
}
