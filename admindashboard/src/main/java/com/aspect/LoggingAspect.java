package com.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around(value = "execution(* com.controller..*(..))")
    public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Thread currentThread = Thread.currentThread();
        logger.info("Method {} is about to execute in thread: {}", joinPoint.getSignature(), currentThread.getName());
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error("Exception in method {}: {}", joinPoint.getSignature(), e.getMessage());
            throw e; // Re-throw exception to propagate
        }
        logger.info("Method {} has executed in thread: {}", joinPoint.getSignature(), currentThread.getName());
        return result;
    }

    @Before(value = "execution(* com.controller..*(..))")
    public void logBeforeMethod() {
        Thread currentThread = Thread.currentThread();
        logger.info("Before method execution in thread: {}", currentThread.getName());
    }

    @After(value = "execution(* com.controller..*(..))")
    public void logAfterMethod() {
        Thread currentThread = Thread.currentThread();
        logger.info("After method execution in thread: {}", currentThread.getName());
    }


    @AfterReturning(value = "execution(* com.controller..*(..))", returning = "result")
    public void logAfterReturningMethod(Object result) {
        Thread currentThread = Thread.currentThread();
        logger.info("Method executed successfully in thread: {}, returned: {}", currentThread.getName(), result);
    }

    @AfterThrowing(value = "execution(* com.controller..*(..))", throwing = "error")
    public void logAfterThrowingMethod(Throwable error) {
        Thread currentThread = Thread.currentThread();
        logger.error("Method threw an exception in thread: {}: {}", currentThread.getName(), error.getMessage());
    }
}
