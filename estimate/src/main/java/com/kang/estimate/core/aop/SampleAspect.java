package com.kang.estimate.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SampleAspect {

    @Pointcut("@annotation(com.kang.estimate.core.aop.Collector)")
    public void cutService(){}

    @Before("cutService()")
    public void doBefore(JoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();
        System.out.println("doBefore");
    }

}
