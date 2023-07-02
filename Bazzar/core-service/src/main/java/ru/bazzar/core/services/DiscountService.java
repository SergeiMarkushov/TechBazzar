package ru.bazzar.core.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bazzar.core.api.DiscountDto;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.entities.Discount;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.repositories.DiscountRepository;
import ru.bazzar.core.utils.ListsForDiscount;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductService productService;
    private ListsForDiscount productsList;

    public void saveDto(DiscountDto discountDto) {
        Discount discount = new Discount();
        discount.setDis(discountDto.getDis());
        discount.setProducts(getProductsList());
        discount.setStartDate(discountDto.getStartDate());
        discount.setExpiryDate(discountDto.getExpiryDate());
        discountRepository.save(discount);
    }

    public void update(DiscountDto discountDto) {
        Discount discount = discountRepository.findById(discountDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Скидка с id: " + discountDto.getId() + " не найдена!"));
        if (discountDto.getDis() != 0) {
            discount.setDis(discountDto.getDis());
        }
        if (discountDto.getStartDate() != null) {
            discount.setStartDate(discountDto.getStartDate());
        }
        if (discountDto.getExpiryDate() != null) {
            discount.setExpiryDate(discountDto.getExpiryDate());
        }
        if (getProductsList() != null) {
            discount.setProducts(getProductsList());
        }
        discountRepository.save(discount);
    }

    @PostConstruct
    public void init() {
        productsList = new ListsForDiscount();
    }

    public List<Product> getProductsList() {
        return productsList.getProducts();
    }

    public void addToList(Long id) {
        productsList.add(productService.findById(id));
    }

    public void removeFromList(Long id) {
        productsList.remove(id);
    }

    public void clearList() {
        productsList.clear();
    }
}
