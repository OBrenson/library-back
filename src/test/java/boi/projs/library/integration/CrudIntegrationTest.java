package boi.projs.library.integration;

import boi.projs.library.configuration.LibraryConfiguration;
import boi.projs.library.domain.Author;
import boi.projs.library.domain.Book;
import boi.projs.library.domain.TextFile;
import boi.projs.library.domain.User;
import boi.projs.library.service.AuthorCrudService;
import boi.projs.library.service.BookCrudService;
import boi.projs.library.service.TextFileCrudService;
import boi.projs.library.service.UserCrudService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Import(LibraryConfiguration.class)
@SpringBootTest
@EnableAspectJAutoProxy
@ComponentScan("boi.projs.library")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CrudIntegrationTest {

    private List<User> users;
    private List<Author> authors = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<TextFile> files = new ArrayList<>();

    @Autowired
    private UserCrudService userCrudService;
    @Autowired
    private BookCrudService bookCrudService;
    @Autowired
    private AuthorCrudService authorCrudService;
    @Autowired
    private TextFileCrudService textFileCrudService;

    @BeforeAll
    private void setup() {
        users = List.of(
                User.builder().login("USERLOGIN1").password("pass".getBytes()).build(),
                User.builder().login("USERLOGIN2").password("pass".getBytes()).build()
        );
        for(int i = 0; i < 4; i++) {
            if(i % 2 == 0) {
                authors.add(Author.builder().name("AUTHOR_" + i).user(users.get(0)).build());
            } else {
                authors.add(Author.builder().name("AUTHOR_" + i).user(users.get(1)).build());
            }
        }

        for(int i = 0; i < 16; i++) {
            for(int j = 3; j >= 0; j--) {
                if(j == 0 || i % j == 0) {
                    books.add(Book.builder().title("BOOK_" + i).author(authors.get(j)).build());
                }
            }
        }

        for (Book book : books) {
            files.add(TextFile.builder().bookId(book.getId()).content("content".getBytes()).build());
        }

        userCrudService.saveAll(users);
        authorCrudService.saveAll(authors);
        bookCrudService.saveAll(books);
        textFileCrudService.saveAll(files);
    }

    @Test
    public void testSelect() {
        List<User> selectedUsers = userCrudService.findAll();
        assertEquals(users.size(), selectedUsers.size());
        for(User user : selectedUsers) {
            assertNotNull(user.getLogin());
            assertArrayEquals("pass".getBytes(), user.getPassword());
            assertNotNull(user.getId());
        }

        User user = userCrudService.findByLoginWithAuthors(users.get(0).getLogin());
    }
}
