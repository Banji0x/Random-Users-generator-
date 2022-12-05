package com.generator.randomusersgenerator.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {
    @Around("execution(* com.paging.paging.services.JwtTokenGenerator.generateJWTTokens(..))")
    public Object JwtTokenGenerator(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("The " + proceedingJoinPoint.getSignature().getName() + " method in " + proceedingJoinPoint.getSignature().getDeclaringTypeName() + " was invoked");
        Object jwtToken = proceedingJoinPoint.proceed();
        log.info("Generated JWT token -> {}", jwtToken);
        return jwtToken;
    }

    @Around("execution(* com.paging.paging.services.DataLoader.commandLineRunner(..))")
    public Object logAuthentication(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object commandLineRunner = proceedingJoinPoint.proceed();
        log.info("Fake details generated and added to database successfully");
        return commandLineRunner;
    }

    @Around("@annotation(com.paging.paging.annotation.SecurityLog)")
    public Object cryptography(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String name = proceedingJoinPoint.getSignature().getName();
        log.info(name + " bean created...");
        return proceedingJoinPoint.proceed();
    }
}
