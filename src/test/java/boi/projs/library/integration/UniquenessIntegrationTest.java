package boi.projs.library.integration;

import boi.projs.library.configuration.LibraryConfiguration;
import boi.projs.library.domain.Author;
import boi.projs.library.domain.Book;
import boi.projs.library.domain.User;
import boi.projs.library.service.AuthorCrudService;
import boi.projs.library.service.BookCrudService;
import boi.projs.library.service.UserCrudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@Import(LibraryConfiguration.class)
@SpringBootTest
@EnableAspectJAutoProxy
@ComponentScan("boi.projs.library")
public class UniquenessIntegrationTest {

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private BookCrudService bookCrudService;

    @Autowired
    private AuthorCrudService authorCrudService;

    @Test
    public void testUserUniqueness() {
        insert();
        User user1 =  User.builder().login("user1").password("pass1".getBytes()).build();
        assertThrows(DataIntegrityViolationException.class, () -> {
            userCrudService.save(user1);
        });
        User user = User.builder().login("user2").password("pass1".getBytes()).build();
        userCrudService.save(user);

        Author author1 = Author.builder().name("author1").user(user1).build();
        assertThrows(DataIntegrityViolationException.class, () -> {
            authorCrudService.save(author1);
        });
        Author author = Author.builder().name("author2").user(user).build();
        authorCrudService.save(author);

        assertThrows(DataIntegrityViolationException.class, () -> {
            bookCrudService.save(
                    Book.builder().title("book1").author(author1).build()
            );
        });
        Book book = Book.builder().title("book1").author(author).build();
        bookCrudService.save(book);
    }

    private void insert() {
        User user = User.builder().login("user1").password("pass1".getBytes()).build();
        userCrudService.save(user);
        Author author = Author.builder().name("author1").user(user).build();
        authorCrudService.save(author);
        Book book = Book.builder().title("book1").author(author).build();
        bookCrudService.save(book);
    }
}
