package ru.bazzar.core.controllers;

import ru.bazzar.core.servises.interf.SimpleService;

public abstract class AbstractRestController <E, K>{

    abstract SimpleService<E,K> getService();

}
