package ru.bazzar.core.configs.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    //перехват работы всех методов ProductService (пример)
    @Before("execution(public * ru.bazzar.core.services.ProductService.*(..))")
    public void beforeAllMethodInProductServiceImplAdvice(){
        System.out.println("Вызван метод, тут будет логирование");
    }


}
