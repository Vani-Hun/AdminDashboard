package com.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    @Before("execution(* com.example.service.*.*(..))")
    public void checkSecurity() {
        System.out.println("Security Check: Method is secure");
    }
}
