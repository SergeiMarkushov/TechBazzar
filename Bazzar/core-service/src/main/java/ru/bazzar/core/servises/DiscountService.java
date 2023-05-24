package ru.bazzar.core.servises;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bazzar.api.DiscountDto;
import ru.bazzar.core.entities.Discount;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.repositories.DiscountRepository;
import ru.bazzar.core.servises.impl.ProductServiceImpl;
import ru.bazzar.core.utils.ListsForDiscount;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountService {
    private final DiscountRepository repository;
    private final ProductServiceImpl productServiceImpl;
    private ListsForDiscount productsList;

    public void save(DiscountDto discountDto) {
        Discount discount = new Discount();
        discount.setDis(discountDto.getDis());
        discount.setProducts(getProductsList());
        discount.setStartDate(discountDto.getStartDate());
        discount.setExpiryDate(discountDto.getExpiryDate());
        repository.save(discount);
    }

    public void update(DiscountDto discountDto) {
        Discount discount = repository.findById(discountDto.getId())
                .orElseThrow(/*() -> new ResourceNotFoundException("Скидка с id: " + discountDto.getId() + " не найдена.")*/);
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
        repository.save(discount);
    }

    @PostConstruct
    public void init() {
        productsList = new ListsForDiscount();
    }

    public List<Product> getProductsList() {
        return productsList.getProducts();
    }

    public void add(Long id) {
        productsList.add(productServiceImpl.findById(id));
    }

    public void remove(Long id) {
        productsList.remove(id);
    }

    public void clear() {
        productsList.clear();
    }
}
