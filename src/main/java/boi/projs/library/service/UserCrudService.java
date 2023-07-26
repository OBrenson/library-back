package boi.projs.library.service;

import boi.projs.library.domain.User;
import boi.projs.library.logging.LoggableCrud;
import boi.projs.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@LoggableCrud
public class UserCrudService extends CrudService<User> {

    @Autowired
    public UserCrudService(UserRepository userRepository) {
        super(userRepository);
    }

    public User findByLoginWithAuthors(String login) {
        return ((UserRepository)repository).findByLoginWithAuthors(login);
    }

    public User findByLogin(String login) {
        return ((UserRepository)repository).findByLogin(login);
    }
}
