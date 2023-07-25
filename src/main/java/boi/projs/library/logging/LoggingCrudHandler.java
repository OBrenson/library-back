package boi.projs.library.logging;

import ch.qos.logback.classic.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingCrudHandler {

    private static final Logger LOGGER =  (Logger) LoggerFactory.getLogger(LoggingCrudHandler.class);

    @Before("@annotation(boi.projs.library.logging.LoggableCrud)")
    public void beforeLogging(JoinPoint joinPoint) {
        LOGGER.info(joinPoint.getSignature().getDeclaringTypeName() + " " + joinPoint.getSignature().getName() + " started");
    }

    @After("@annotation(boi.projs.library.logging.LoggableCrud)")
    public void afterLogging(JoinPoint joinPoint) {
        LOGGER.info(joinPoint.getSignature().getDeclaringTypeName() + " " + joinPoint.getSignature().getName() + " ended");
    }
}
