package boi.projs.library.integration;

import boi.projs.library.configuration.LibraryConfiguration;
import boi.projs.library.domain.*;
import boi.projs.library.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Import(LibraryConfiguration.class)
@SpringBootTest
@EnableAspectJAutoProxy
@ComponentScan("boi.projs.library")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CrudIntegrationTest {

    private List<User> users;
    private final List<Author> authors = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();
    private final List<TextFile> files = new ArrayList<>();

    @Autowired
    private UserCrudService userCrudService;
    @Autowired
    private BookCrudService bookCrudService;
    @Autowired
    private AuthorCrudService authorCrudService;
    @Autowired
    private TextFileCrudService textFileCrudService;

    @BeforeAll
    public void setup() {
        users = List.of(
                User.builder().login("USERLOGIN1").password("pass".getBytes()).build(),
                User.builder().login("USERLOGIN2").password("pass".getBytes()).build()
        );
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                authors.add(Author.builder().name("AUTHOR_" + i).user(users.get(0)).build());
            } else {
                authors.add(Author.builder().name("AUTHOR_" + i).user(users.get(1)).build());
            }
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 3; j >= 0; j--) {
                if (j == 0 || i % j == 0) {
                    books.add(Book.builder().title("BOOK_" + i).author(authors.get(j))
                            .fileId(UUID.randomUUID()).build());
                }
            }
        }

        Random rand = new Random();

        for (Book book : books) {
            files.add(TextFile.builder().bookId(book.getId())
                    .content(("content" + rand.nextInt()).getBytes()).build());
        }

        userCrudService.saveAll(users);
        authorCrudService.saveAll(authors);
        bookCrudService.saveAll(books);
        textFileCrudService.saveAll(files);
    }

    @Test
    public void testUserSelect() {
        List<User> selectedUsers = userCrudService.findAll();
        assertEquals(users.size(), selectedUsers.size());
        for (User user : selectedUsers) {
            assertNotNull(user.getLogin());
            assertArrayEquals("pass".getBytes(), user.getPassword());
            assertNotNull(user.getId());
        }

        User user = userCrudService.findByLoginWithAuthors(users.get(0).getLogin());
        assertEquals(2, user.getAuthors().size());

        user = userCrudService.findByLogin(users.get(1).getLogin());
        assertEquals(users.get(1).getLogin(), user.getLogin());
        assertArrayEquals(users.get(1).getPassword(), user.getPassword());
    }

    @Test
    public void testAuthorSelect() {
        List<Author> selectedAuthors = authorCrudService.findAll();
        assertEquals(authors.size(), selectedAuthors.size());
        for (Author author : selectedAuthors) {
            assertTrue(authors.contains(author));
        }

        Author author = authorCrudService.findByNameAndUserId(authors.get(0).getName(),
                authors.get(0).getUser().getId());
        assertEquals(authors.get(0), author);

        selectedAuthors = authorCrudService.findByNameWithBooks(authors.get(0).getName());
        assertEquals(1, selectedAuthors.size());
        assertEquals(books.stream().filter(e -> e.getAuthor().equals(authors.get(0))).count(),
                selectedAuthors.get(0).getBooks().size());

        selectedAuthors = authorCrudService.findByUserId(users.get(0).getId());
        assertEquals(2, selectedAuthors.size());
        for (Author a : selectedAuthors) {
            assertEquals(users.get(0).getId(), a.getUser().getId());
        }

        selectedAuthors = authorCrudService.findByUserLogin(users.get(0).getLogin());
        assertEquals(2, selectedAuthors.size());
        for (Author a : selectedAuthors) {
            assertEquals(users.get(0).getId(), a.getUser().getId());
        }
    }

    @Test
    public void testBookSelect() {
        List<Book> selectedBooks = bookCrudService.findAll();
        assertEquals(books.size(), selectedBooks.size());
        for (Book book : selectedBooks) {
            assertTrue(books.contains(book));
        }

        selectedBooks = bookCrudService.findByAuthorId(authors.get(0).getId());
        assertEquals(books.stream().filter(e -> authors.get(0).equals(e.getAuthor())).count(),
                selectedBooks.size());
        for (Book b : selectedBooks) {
            assertEquals(authors.get(0).getId(), b.getAuthor().getId());
        }

        selectedBooks = bookCrudService.findByAuthorName(authors.get(1).getName());
        assertEquals(books.stream().filter(e -> authors.get(1).equals(e.getAuthor())).count(),
                selectedBooks.size());
        for (Book b : selectedBooks) {
            assertEquals(authors.get(1).getId(), b.getAuthor().getId());
        }
    }

    @Test
    public void testTextFileSelect() {
        List<TextFile> selectedTextFiles = textFileCrudService.findAll();
        assertEquals(files.size(), selectedTextFiles.size());
        for (TextFile file : selectedTextFiles) {
            assertTrue(files.contains(file));
        }

        TextFile file = textFileCrudService.findByBookId(files.get(0).getBookId());
        assertNotNull(file);
        assertEquals(files.get(0), file);
    }

    @Test
    public void testUserUpdate() {
        final String testLogin = "new_LOGIN";
        load_change_save_return_old_save(userCrudService, users.get(0),
                user -> assertEquals(testLogin, user.getLogin()),
                user -> {
                    user.setLogin(testLogin);
                    return user;
                },
                user -> {
                    user.setLogin(users.get(0).getLogin());
                    return user;
                });
    }

    @Test
    public void testAuthorUpdate() {
        final String testName = "NEW_NAME";
        load_change_save_return_old_save(authorCrudService, authors.get(0),
                author -> assertEquals(testName, author.getName()),
                author -> {
                    author.setName(testName);
                    return author;
                },
                author -> {
                    author.setName(authors.get(0).getName());
                    return author;
                });
    }

    @Test
    public void testBookUpdate() {
        final String testTitle = "new_TITLE";
        load_change_save_return_old_save(bookCrudService, books.get(0),
                book -> assertEquals(testTitle, book.getTitle()),
                book -> {
                    book.setTitle(testTitle);
                    return book;
                },
                book -> {
                    book.setTitle(books.get(0).getTitle());
                    return book;
                });
    }

    @Test
    public void testTextFileUpdate() {
        final byte[] testContent = "asd123".getBytes();
        load_change_save_return_old_save(textFileCrudService, files.get(0),
                file -> assertArrayEquals(testContent, file.getContent()),
                file -> {
                    file.setContent(testContent);
                    return file;
                },
                file -> {
                    file.setContent(files.get(0).getContent());
                    return file;
                });
    }

    @Test
    public void testUserDelete() {
        User user = users.get(0);
        assertTrue(delete_load_check_is_null(userCrudService, user).isEmpty());

        List<Author> removedAuthors = authors.stream().filter(e -> e.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        List<Book> removedBooks = books.stream().filter(e -> e.getAuthor().getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        userCrudService.save(user);
        authorCrudService.saveAll(removedAuthors);
        bookCrudService.saveAll(removedBooks);
    }

    @Test
    public void testAuthorDelete() {
        Author author = authors.get(0);
        assertTrue(delete_load_check_is_null(authorCrudService, authors.get(0)).isEmpty());
        List<Book> removedBooks =
                books.stream().filter(e -> e.getAuthor().getId().equals(author.getId()))
                        .collect(Collectors.toList());
        authorCrudService.save(author);
        bookCrudService.saveAll(removedBooks);
    }

    @Test
    public void testBookDelete() {
        Book book = books.get(0);
        assertTrue(delete_load_check_is_null(bookCrudService, book).isEmpty());
        bookCrudService.save(book);
    }

    @Test
    public void testTextFileDelete() {
        assertTrue(delete_load_check_is_null(textFileCrudService, files.get(0)).isEmpty());
        textFileCrudService.save(files.get(0));
    }

    private <T extends BaseEntity> Optional<T> delete_load_check_is_null(CrudService<T> crudService, T entity) {
        crudService.deleteById(entity.getId());
        return crudService.findById(entity.getId());
    }

    /**
     * @param crudService - сервис для работы с базой для сущности типа T.
     * @param check       - проверка равенства
     * @param setNewValue - проставление нового значение
     * @param setOldValue - проставление старого значение
     */
    private <T extends BaseEntity> void load_change_save_return_old_save(
            CrudService<T> crudService,
            T oldEntity,
            Consumer<T> check,
            Function<T, T> setNewValue,
            Function<T, T> setOldValue) {
        T entity = crudService.findById(oldEntity.getId()).orElseThrow();
        entity = setNewValue.apply(entity);
        crudService.save(entity);
        entity = crudService.findById(oldEntity.getId()).orElseThrow();
        check.accept(entity);
        entity = setOldValue.apply(entity);
        crudService.save(entity);
    }
}
