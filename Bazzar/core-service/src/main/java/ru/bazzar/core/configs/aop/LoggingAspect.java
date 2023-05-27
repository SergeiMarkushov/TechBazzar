package ru.bazzar.core.configs.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    //перехват работы всех методов ProductServiceImpl (пример)
    @Before("execution(public * ru.bazzar.core.services.impl.ProductServiceImpl.*(..))")
    public void beforeAllMethodInProductServiceImplAdvice(){
        System.out.println("Вызван метод, тут будет логирование");
    }


}
