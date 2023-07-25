package boi.projs.library.configuration;

import boi.projs.library.logging.LoggingCrudHandler;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryConfiguration {

    @Bean
    public Logger getCrudLogger() {
        return (Logger)LoggerFactory.getLogger(LoggingCrudHandler.class);
    }
}
