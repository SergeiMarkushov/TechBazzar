package ru.bazzar.core.services.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import javax.validation.Valid;


public abstract class AbstractService<E>{

    //Общая валидация(наследуются, реализуют repo.save(), а тут - @Valid)
    abstract E validSaveAndReturn(@Valid E entity);
}
