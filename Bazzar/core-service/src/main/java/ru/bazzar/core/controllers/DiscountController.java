package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.api.DiscountDto;
import ru.bazzar.core.entities.Discount;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.servises.impl.DiscountServiceImpl;
import ru.bazzar.core.servises.interf.SimpleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discount")
public class DiscountController extends AbstractRestController<Discount,Long> {
    private final DiscountServiceImpl discountServiceImpl;

    @Override
    SimpleService<Discount, Long> getService() {
        return discountServiceImpl;
    }

    @PostMapping("/new")
    public void saveDto(@RequestBody DiscountDto discountDto) {
        discountServiceImpl.saveDto(discountDto);
    }

    @PostMapping("/update")
    public void updateDto(@RequestBody DiscountDto discountDto) {
        discountServiceImpl.update(discountDto);
    }

    @GetMapping
    public List<Product> getProductsList() {
        return discountServiceImpl.getProductsList();
    }

    @GetMapping("/add/{id}")
    public void addToList(@PathVariable Long id) {
        discountServiceImpl.addToList(id);
    }

    @GetMapping("/remove/{id}")
    public void deleteByIdFromList(@PathVariable Long id) {
        discountServiceImpl.removeFromList(id);
    }

    @GetMapping("/clear")
    public void clear() {
        discountServiceImpl.clearList();
    }
    //deleteById
}
