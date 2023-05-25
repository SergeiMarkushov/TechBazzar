package ru.bazzar.core.servises.interf;
//От него идет имплементация на остальные interf-сервисы(интерфейсы)
public interface SimpleService<E,K> {
    E findById(K id);
    void save(E entity);
    void deleteById(K id);
}
