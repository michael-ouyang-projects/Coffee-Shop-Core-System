package com.ouyang.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Aspect
@Component
public class AopPointcut {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopPointcut.class);

    @Pointcut("within(com.ouyang.mvc.controller.*) && @within(org.springframework.stereotype.Controller)")
    public void controllerLayer() {
    }

    @Pointcut("execution(public String *(..))")
    public void publicMethod() {
    }

    @Pointcut("controllerLayer() && publicMethod()")
    public void controllerPublicMethod() {
    }

    @Around("controllerPublicMethod()")
    public String processRequest(ProceedingJoinPoint joinPoint) {

        try {

            return (String) joinPoint.proceed();

        } catch (Throwable e) {

            LOGGER.info("{}", e.getMessage());
            RequestContextHolder.getRequestAttributes().setAttribute("errorMessage", e.getMessage(), 0);
            return "error.html";

        }

    }

}
