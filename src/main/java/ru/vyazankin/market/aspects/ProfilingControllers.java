package ru.vyazankin.market.aspects;

import lombok.Getter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

//@Aspect
//@Component
public class ProfilingControllers {

    @Getter
    private Map<String, Long> controllersMap;

    @PostConstruct
    private void init(){
        controllersMap = new HashMap<>();
    }

    @Around("execution(* ru.vyazankin.market.controller..*(..))")
    public Object methodProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long begin = System.currentTimeMillis();
        String name = proceedingJoinPoint.getTarget().getClass().getName();

        Object ret = proceedingJoinPoint.proceed();

        long mils = System.currentTimeMillis() - begin;

        Long prevmils = controllersMap.get(name);

        if (prevmils == null) prevmils = 0L;

        controllersMap.put(name, prevmils + mils);

        return ret;
    }


}