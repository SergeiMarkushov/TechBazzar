package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.core.api.DiscountDto;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.services.impl.DiscountServiceImpl;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final DiscountServiceImpl discountServiceImpl;

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

}
