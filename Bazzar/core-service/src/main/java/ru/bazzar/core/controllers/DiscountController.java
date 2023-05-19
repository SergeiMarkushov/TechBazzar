package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.api.DiscountDto;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.servises.DiscountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping("/new")
    public void save(@RequestBody DiscountDto discountDto) {
        discountService.save(discountDto);
    }

    @PostMapping("/update")
    public void update(@RequestBody DiscountDto discountDto) {
        discountService.update(discountDto);
    }

    @GetMapping
    public List<Product> getProductsList() {
        return discountService.getProductsList();
    }

    @GetMapping("/add/{id}")
    public void add(@PathVariable Long id) {
        discountService.add(id);
    }

    @GetMapping("/remove/{id}")
    public void remove(@PathVariable Long id) {
        discountService.remove(id);
    }

    @GetMapping("/clear")
    public void clear() {
        discountService.clear();
    }
}
