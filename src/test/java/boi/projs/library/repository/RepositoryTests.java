package boi.projs.library.repository;

import boi.projs.library.domain.Author;
import boi.projs.library.domain.BaseEntity;
import boi.projs.library.domain.DomainTests;
import boi.projs.library.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static boi.projs.library.Constants.*;

@DataJpaTest
@TestPropertySource(locations="classpath:application.properties")
public class RepositoryTests {

    private final static Long defaultId = 123L;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testUserRep() {
        User user = User.builder().login("test_login").password("pass".getBytes()).id(UUID.randomUUID()).build();
        testStandardFunctions(userRepository, user);

        user = User.builder().login(userName).password(userPass).id(UUID.randomUUID()).build();
        UUID userId = userRepository.save(user).getId();
        user = userRepository.findByLogin(userName);
        assertNotNull(user);
        assertEquals(userName, user.getLogin());
        assertArrayEquals(userPass, user.getPassword());
        assertEquals(userId, user.getId());

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAndFlush(createUser()));
    }

    @Test
    public void testAuthorRep() {
        User user = createUser();
        Author author = createAuthor(user);
        authorRepository.save(author);

        final Author duplicateAuthor = createAuthor(user);
        assertThrows(DataIntegrityViolationException.class, () -> authorRepository.saveAndFlush(duplicateAuthor));

        Author sameAuthorWithAnotherUser = createAuthor(User.builder()
                .id(UUID.randomUUID())
                .login("newUser")
                .password(userPass)
                .build()
        );
        authorRepository.save(sameAuthorWithAnotherUser);

    }

    public <T extends BaseEntity>void testStandardFunctions(JpaRepository<T, UUID> repository, T entity) {
        UUID uuid = repository.save(entity).getId();
        T result = repository.getReferenceById(uuid);
        assertNotNull(result);
        assertEquals(uuid, result.getId());
    }
}
