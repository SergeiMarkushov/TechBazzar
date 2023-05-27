package ru.bazzar.core.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.bazzar.core.services.interf.SimpleService;

public abstract class AbstractRestController <E, K>{

    abstract SimpleService<E,K> getService();

    @DeleteMapping("/{id}") //---проверить потом, пока используется только в OrderController
    public void deleteById(@PathVariable K id) {
        getService().deleteById(id);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable K id) {
//
//        E objectForDelete = getService().findById(id);
//        getService().delete(objectForDelete);
//        return ResponseEntity.noContent().build();
//    }
}
