package ru.vyazankin.market.aspects;

import lombok.Getter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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

    @Before("execution(* ru.vyazankin.market.controller..*(..))")
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
