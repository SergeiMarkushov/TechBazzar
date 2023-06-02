package ru.bazzar.core.configs.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.utils.IdentityMap;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class IdentityMapAspect {

    //тест работы(пример)
    @Before("execution(public void ru.bazzar.core.controllers.ProductController.testProduct()))")
    public void beforeTestAdvice(){
        System.out.println("@Aspect beforeTestAdvice - попытка получить testProduct()!");
    }


}
