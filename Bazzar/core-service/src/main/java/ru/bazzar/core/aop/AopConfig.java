package ru.bazzar.core.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("ru.bazzar.core.aop")
@EnableAspectJAutoProxy
public class AopConfig {
}
