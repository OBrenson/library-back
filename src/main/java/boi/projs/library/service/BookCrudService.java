package boi.projs.library.service;

import boi.projs.library.domain.Book;
import boi.projs.library.logging.LoggableCrud;
import boi.projs.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@LoggableCrud
public class BookCrudService extends CrudService<Book> {

    @Autowired
    public BookCrudService(BookRepository bookRepository) {
        super(bookRepository);
    }
}
