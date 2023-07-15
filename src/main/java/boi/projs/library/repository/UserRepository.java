package boi.projs.library.repository;

import boi.projs.library.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByLogin(String login);

    @Query("select u from User u join fetch u.authors a where u.login = ?1")
    User findByLoginWithAuthors(String login);
}
