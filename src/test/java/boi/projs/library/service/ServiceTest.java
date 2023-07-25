package boi.projs.library.service;

import boi.projs.library.Constants;
import boi.projs.library.configuration.LibraryConfiguration;
import boi.projs.library.domain.User;
import boi.projs.library.logging.LoggingCrudHandler;
import boi.projs.library.repository.UserRepository;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
//    @MockBean
//    private BookRepository bookRepository;
//    @MockBean
//    private AuthorRepository authorRepository;
//    @MockBean
//    private TextFileRepository textFileRepository;

    private MemoryAppender memoryAppender;

    @Autowired
    private UserCrudService userCrudService;
//    @Autowired
//    private AuthorCrudService authorCrudService;
//    @Autowired
//    private BookCrudService bookCrudService;
//    @Autowired
//    private TextFileCrudService textFileCrudService;

    private List<CrudService<?>> crudServicesList = new ArrayList<>();

    @BeforeEach
    public void setup() throws IllegalAccessException {
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        crudLogger.setLevel(Level.DEBUG);
        crudLogger.addAppender(memoryAppender);
        memoryAppender.start();

        when(userRepository.findById(userId)).thenReturn(Optional.of(createUser()));
        when(userRepository.findAll()).thenReturn(List.of(createUser()));

        crudServicesList.add(userCrudService);
    }

    @Test
    public void testBaseService() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(createUser()));
        User user = Constants.createUser();

        userCrudService.save(user);
        Optional<User> oUser = userCrudService.findById(userId);
        assertTrue(oUser.isPresent());
        List<User> users = userCrudService.findAll();
        assertEquals(1, users.size());

        userCrudService.deleteById(Constants.userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).findAll();
        verify(userRepository, times(1)).deleteById(userId);

        String loggerName = LoggingCrudHandler.class.getName();
        assertEquals(11, memoryAppender.countEventsForLogger(loggerName));
        assertEquals(8, memoryAppender.countEventsForLoggerByLevel(loggerName, Level.INFO));
        assertEquals(3, memoryAppender.countEventsForLoggerByLevel(loggerName, Level.DEBUG));
    }

}
