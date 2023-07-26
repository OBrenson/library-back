package boi.projs.library;

import boi.projs.library.configuration.LibraryConfiguration;
import boi.projs.library.domain.User;
import boi.projs.library.repository.UserRepository;
import boi.projs.library.service.UserCrudService;
import ch.qos.logback.classic.Logger;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@Import(LibraryConfiguration.class)
@SpringBootTest
@EnableAspectJAutoProxy
@ComponentScan("boi.projs.library")
@RunWith(SpringRunner.class)
public class CrudServicesIntegrationTest {

    @Autowired
    private Logger crudLogger;
    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUsersUniqueness() {
        insertUsers();
        userCrudService.save(User.builder().id(UUID.randomUUID()).login("user1").password("pass1".getBytes()).build());
    }

    private void insertUsers() {
        userCrudService.save(User.builder().id(UUID.randomUUID()).login("user1").password("pass1".getBytes()).build());
    }
}
