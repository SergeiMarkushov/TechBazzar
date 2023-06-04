package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.core.api.DiscountDto;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.services.DiscountService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping("/new")//AOP
    public void saveDto(@RequestBody DiscountDto discountDto) {
        discountService.saveDto(discountDto);
    }

    @PostMapping("/update")//AOP
    public void updateDto(@RequestBody DiscountDto discountDto) {
        discountService.update(discountDto);
    }

    @GetMapping
    public List<Product> getProductsList() {
        return discountService.getProductsList();
    }

    @GetMapping("/add/{id}")//AOP
    public void addToList(@PathVariable Long id) {
        discountService.addToList(id);
    }

    @GetMapping("/remove/{id}")//AOP
    public void deleteByIdFromList(@PathVariable Long id) {
        discountService.removeFromList(id);
    }

    @GetMapping("/clear")//AOP
    public void clear() {
        discountService.clearList();
    }

}
