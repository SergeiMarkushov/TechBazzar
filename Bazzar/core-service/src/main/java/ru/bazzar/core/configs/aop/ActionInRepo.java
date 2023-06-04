package ru.bazzar.core.configs.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class ActionInRepo {
    //добавление логирования в файл каждого - вызова .save() во всех репозиториях.

    @Before("execution(public * ru.bazzar.core.repositories.*.save(..))")
    public void beforeSaveAdvice(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        StringBuilder sbArgs = new StringBuilder();
        for (Object o : args) {
            sbArgs.append(o).append(" ");
        }
        log.warn(methodSignature.getName() + ": " + sbArgs);
    }

}
