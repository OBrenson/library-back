package boi.projs.library.service;

import boi.projs.library.domain.Author;
import boi.projs.library.logging.LoggableCrud;
import boi.projs.library.repository.AuthorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@LoggableCrud
public class AuthorCrudService extends CrudService<Author> {

    public AuthorCrudService(AuthorRepository authorRepository) {
        super(authorRepository);
    }
}
