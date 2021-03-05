package ru.vyazankin.market.aspects;

import lombok.Getter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ProfilingMethods {

    @Getter
    private Map<String, Long> methodMap;

    @PostConstruct
    private void init(){
        methodMap = new HashMap<>();
    }

    @Pointcut("execution(* ru.vyazankin.market.bean..*(..))")
    public void beansPointcut(){}

    @Pointcut("execution(* ru.vyazankin.market.controller..*(..))")
    public void controllerPointcut(){}

    @Pointcut("execution(* ru.vyazankin.market.dto..*(..))")
    public void dtoPointcut(){}

    @Pointcut("execution(* ru.vyazankin.market.model..*(..))")
    public void modelPointcut(){}

    @Pointcut("execution(* ru.vyazankin.market.repository..*(..))")
    public void repositoryPointcut(){}

    @Pointcut("execution(* ru.vyazankin.market.service..*(..))")
    public void servicePointcut(){}


    @Pointcut("beansPointcut() || controllerPointcut() || dtoPointcut() " +
            "|| modelPointcut() || repositoryPointcut() || servicePointcut()")
    public void allPackagesPointcut(){}

    //@Before("execution(* ru.vyazankin.market.controller..*(..))")
    @Before("allPackagesPointcut()")
    private void beforeAnyMethod(JoinPoint joinPoint){
        String name = joinPoint.getStaticPart() + "." + joinPoint.getSignature().getName();

        Long count = methodMap.get(name);
        if (count == null){
            methodMap.put(name, 1L);
        } else {
            methodMap.put(name, ++count);
        }
    }


}
