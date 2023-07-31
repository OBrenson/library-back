package boi.projs.library.service;

import boi.projs.library.domain.Author;
import boi.projs.library.logging.LoggableCrud;
import boi.projs.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@LoggableCrud
public class AuthorCrudService extends CrudService<Author> {

    public AuthorCrudService(AuthorRepository authorRepository) {
        super(authorRepository);
    }

    public Author findByNameAndUserId(String name, UUID id) {
        return ((AuthorRepository)repository).findByNameAndUser_Id(name, id);
    }

    public List<Author> findByUserId(UUID id){
        return ((AuthorRepository)repository).findByUserId(id);
    }

    public List<Author> findByUserLogin(String login){
        return ((AuthorRepository)repository).findByUserLogin(login);
    }

    public List<Author> findByNameWithBooks(String name){
        return ((AuthorRepository)repository).findByNameWithBooks(name);
    }
}
