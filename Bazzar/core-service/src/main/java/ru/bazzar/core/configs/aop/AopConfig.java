package ru.bazzar.core.configs.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("ru.bazzar.core.configs.aop")
@EnableAspectJAutoProxy
public class AopConfig {
}
