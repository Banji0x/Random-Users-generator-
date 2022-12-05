package com.generator.randomusersgenerator.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.generator.randomusersgenerator.services.JwtTokenGenerator.generateJWTTokens(..))")
    public Object JwtTokenGenerator(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("The " + proceedingJoinPoint.getSignature().getName() + " method in " + proceedingJoinPoint.getSignature().getDeclaringTypeName() + " was invoked");
        Object jwtToken = proceedingJoinPoint.proceed();
        log.info("Generated JWT token -> {}", jwtToken);
        return jwtToken;
    }

    @Around("execution(* com.generator.randomusersgenerator.services.DataLoader.commandLineRunner(..))")
    public Object logAuthentication(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object commandLineRunner = proceedingJoinPoint.proceed();
        log.info("Fake details generated and added to database successfully");
        return commandLineRunner;
    }

    @Around("@annotation(com.generator.randomusersgenerator.annotation.SecurityLog)")
    public Object cryptography(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String name = proceedingJoinPoint.getSignature().getName();
        log.info(name + " bean created...");
        return proceedingJoinPoint.proceed();
    }
}
