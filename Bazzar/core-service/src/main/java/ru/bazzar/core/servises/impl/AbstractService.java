package ru.bazzar.core.servises.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bazzar.core.servises.interf.SimpleService;

public abstract class AbstractService<E, K> implements SimpleService<E, K> {
    abstract JpaRepository<E,K> getJpaRepository();

    @Override
    public E findById(K id) {
        return getJpaRepository()
                .findById(id)
                .orElse(null);
        /*
        подумать над throw и над реализацией
          identityMap для всех сущностей
          удобно - сразу тут будет мапирование
          для всех сервисов, не нужно реализацию писать...
        */
    }

    @Override
    public void save(E object) {
        getJpaRepository().save(object);

    }

    @Override
    public void delete(E object) {
        getJpaRepository().delete(object);
    }
}
