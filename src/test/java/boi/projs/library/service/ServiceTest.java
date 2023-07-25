package boi.projs.library.service;

import boi.projs.library.Constants;
import boi.projs.library.domain.User;
import boi.projs.library.repository.UserRepository;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static boi.projs.library.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@EnableAspectJAutoProxy
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private UserCrudService baseService;

    @MockBean
    private UserRepository userRepository;

    private MemoryAppender memoryAppender;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(CrudService.class.getName());
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);
        memoryAppender.start();

        when(userRepository.findById(userId)).thenReturn(Optional.of(createUser()));
        when(userRepository.findAll()).thenReturn(List.of(createUser()));
    }

    @Test
    public void testBaseService() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(createUser()));
        User user = Constants.createUser();

        baseService.save(user);
        Optional<User> oUser = baseService.findById(userId);
        assertTrue(oUser.isPresent());
        List<User> users = baseService.findAll();
        assertEquals(1, users.size());

        baseService.deleteById(Constants.userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).findAll();
        verify(userRepository, times(1)).deleteById(userId);

        assertThat(memoryAppender.countEventsForLogger(CrudService.class.getName())).isEqualTo(3);
        assertThat(memoryAppender.search(LOG_MSG).size()).isEqualTo(1);
        assertThat(memoryAppender.contains(LOG_MSG, Level.DEBUG)).isFalse();
    }

}
