package boi.projs.library.service;

import boi.projs.library.Constants;
import boi.projs.library.configuration.LibraryConfiguration;
import boi.projs.library.domain.User;
import boi.projs.library.logging.LoggingCrudHandler;
import boi.projs.library.repository.AuthorRepository;
import boi.projs.library.repository.BookRepository;
import boi.projs.library.repository.TextFileRepository;
import boi.projs.library.repository.UserRepository;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    Map<CrudService, JpaRepository> serviceRepos = new HashMap<>();

    @BeforeEach
    public void setup() throws IllegalAccessException {
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
        for(Map.Entry<CrudService, JpaRepository> entry : serviceRepos.entrySet()) {
            testServices(entry.getKey(), entry.getValue());
        }

        String loggerName = LoggingCrudHandler.class.getName();
        assertEquals(32, memoryAppender.countEventsForLogger(loggerName));
        assertEquals(24, memoryAppender.countEventsForLoggerByLevel(loggerName, Level.INFO));
        assertEquals(8, memoryAppender.countEventsForLoggerByLevel(loggerName, Level.DEBUG));
    }

    public void testServices(CrudService crudService, JpaRepository repository) {
        User user = Constants.createUser();

        crudService.findById(userId);
        crudService.findAll();

        crudService.deleteById(Constants.userId);

        verify(repository, times(1)).findById(userId);
        verify(repository, times(1)).findAll();
        verify(repository, times(1)).deleteById(userId);
    }

}
