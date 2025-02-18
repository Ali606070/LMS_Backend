package com.library.task.aspects;

import com.library.task.security.CustomUserDetails;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.library.task.services.*.*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Long userId = getCurrentUserId(); // Get the current user ID

        logger.info("Entering method: {}.{} by user: {}", className, methodName, userId);

        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            logger.info("Exiting method: {}.{} by user: {} with execution time: {} ms", className, methodName, userId, (endTime - startTime));
            return result;
        } catch (Exception e) {
            logger.error("Exception in method: {}.{} by user: {} with message: {}", className, methodName, userId, e.getMessage());
            throw e;
        }
    }

    private Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getId(); // Extract user ID as Long
        } else {
            logger.warn("Unable to retrieve user ID: Logging in attempt without token.");
            return null;
        }
    }
}