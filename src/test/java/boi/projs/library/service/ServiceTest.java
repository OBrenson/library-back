package boi.projs.library.service;

import boi.projs.library.Constants;
import boi.projs.library.configuration.LibraryConfiguration;
import boi.projs.library.domain.Author;
import boi.projs.library.domain.Book;
import boi.projs.library.domain.User;
import boi.projs.library.logging.LoggingCrudHandler;
import boi.projs.library.repository.AuthorRepository;
import boi.projs.library.repository.BookRepository;
import boi.projs.library.repository.TextFileRepository;
import boi.projs.library.repository.UserRepository;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

import static boi.projs.library.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Import(LibraryConfiguration.class)
@EnableAspectJAutoProxy
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceTest {

    @Autowired
    private Logger crudLogger;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private TextFileRepository textFileRepository;

    private MemoryAppender memoryAppender;

    @Autowired
    private UserCrudService userCrudService;
    @Autowired
    private AuthorCrudService authorCrudService;
    @Autowired
    private BookCrudService bookCrudService;
    @Autowired
    private TextFileCrudService textFileCrudService;

    Map<CrudService<?>, JpaRepository<?, UUID>> serviceRepos = new HashMap<>();

    @BeforeAll
    public void setup() {
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        crudLogger.setLevel(Level.DEBUG);
        crudLogger.addAppender(memoryAppender);
        memoryAppender.start();

        serviceRepos.put(userCrudService,userRepository);
        serviceRepos.put(authorCrudService,authorRepository);
        serviceRepos.put(bookCrudService,bookRepository);
        serviceRepos.put(textFileCrudService,textFileRepository);
    }

    @Test
    public void testBaseService() {
        for(Map.Entry<CrudService<?>, JpaRepository<?, UUID>> entry : serviceRepos.entrySet()) {
            testServices(entry.getKey(), entry.getValue());
        }

        String loggerName = LoggingCrudHandler.class.getName();
        assertEquals(32, memoryAppender.countEventsForLogger(loggerName));
        assertEquals(24, memoryAppender.countEventsForLoggerByLevel(loggerName, Level.INFO));
        assertEquals(8, memoryAppender.countEventsForLoggerByLevel(loggerName, Level.DEBUG));
    }

    @Test
    public void testAuthorService() {
        Author author = createAuthor(createUser());
        when(authorRepository.findByNameAndUser_Id(authorName, userId)).thenReturn(author);
        when(authorRepository.findByNameWithBooks(authorName)).thenReturn(List.of(author));
        when(authorRepository.findByUserId(userId)).thenReturn(List.of(author));
        when(authorRepository.findByUserLogin(userName)).thenReturn(List.of(author));

        authorCrudService.findByNameAndUserId(authorName, userId);
        authorCrudService.findByNameWithBooks(authorName);
        authorCrudService.findByUserId(userId);
        authorCrudService.findByUserLogin(userName);

        verify(authorRepository, times(1)).findByNameAndUser_Id(authorName, userId);
        verify(authorRepository, times(1)).findByNameWithBooks(authorName);
        verify(authorRepository, times(1)).findByUserId(userId);
        verify(authorRepository, times(1)).findByUserLogin(userName);
    }

    @Test
    public void testBookService() {
        Book book = Book.builder().author(createAuthor(createUser())).id(bookId).title(bookTitle).build();

        when(bookRepository.findByAuthorId(authorId)).thenReturn(List.of(book));
        when(bookRepository.findByAuthorName(authorName)).thenReturn(List.of(book));

        bookCrudService.findByAuthorId(authorId);
        bookCrudService.findByAuthorName(authorName);

        verify(bookRepository, times(1)).findByAuthorId(authorId);
        verify(bookRepository, times(1)).findByAuthorName(authorName);
    }

    @Test
    public void testUserService() {
        User user = createUser();

        when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
        when(userRepository.findByLoginWithAuthors(user.getLogin())).thenReturn(user);

        userCrudService.save(user);
        userCrudService.findByLoginWithAuthors(user.getLogin());
        userCrudService.findByLogin(user.getLogin());

        verify(userRepository, times(1)).findByLogin(user.getLogin());
        verify(userRepository, times(1)).findByLoginWithAuthors(user.getLogin());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testTextFileService() {
        textFileCrudService.findByBookId(bookId);

        verify(textFileRepository, times(1)).findByBookId(bookId);
    }

    private void testServices(CrudService<?> crudService, JpaRepository<?, UUID> repository) {
        crudService.findById(userId);
        crudService.findAll();

        crudService.deleteById(Constants.userId);

        verify(repository, times(1)).findById(userId);
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).deleteById(userId);
    }

}
