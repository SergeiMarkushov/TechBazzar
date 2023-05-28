package ru.bazzar.core.services.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bazzar.core.services.interf.SimpleService;

public abstract class AbstractService<E, K> implements SimpleService<E, K> {
    abstract JpaRepository<E,K> getRepository();

    @Override
    public E findById(K id) {
        return getRepository()
                .findById(id)
                .orElseThrow();
    }

    @Override
    public void save(E object) {
        getRepository().save(object);
    }

    @Override
    public void deleteById(K id) {
        getRepository().deleteById(id);
    }
}
