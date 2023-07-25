package boi.projs.library.logging;

import ch.qos.logback.classic.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingCrudHandler {

    private final Logger crudLogger;

    @Autowired
    public LoggingCrudHandler(Logger crudLogger) {
        this.crudLogger = crudLogger;
    }

    @Before("@annotation(boi.projs.library.logging.LoggableCrud)")
    public void beforeLogging(JoinPoint joinPoint) {
        crudLogger.info("{}#{}",joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName() + " started");
        for(Object arg : joinPoint.getArgs()) {
            crudLogger.debug(arg.toString());
        }
    }

    @After("@annotation(boi.projs.library.logging.LoggableCrud)")
    public void afterLogging(JoinPoint joinPoint) {
        crudLogger.info("{}#{}  successfully ended", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }
}
