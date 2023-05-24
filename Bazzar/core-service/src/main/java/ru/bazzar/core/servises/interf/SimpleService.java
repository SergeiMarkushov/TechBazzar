package ru.bazzar.core.servises.interf;

public interface SimpleService<E,K> {
    E findById(K id);
    void save(E entity);
    void delete(E entity);
}
