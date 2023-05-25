package ru.bazzar.core.servises.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.bazzar.api.DiscountDto;
import ru.bazzar.api.ResourceNotFoundException;
import ru.bazzar.core.entities.Discount;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.repositories.DiscountRepository;
import ru.bazzar.core.servises.interf.DiscountService;
import ru.bazzar.core.utils.ListsForDiscount;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl extends AbstractService<Discount, Long> implements DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductServiceImpl productServiceImpl;
    private ListsForDiscount productsList;

    @Override
    JpaRepository<Discount, Long> getRepository() {
        return discountRepository;
    }

    public void saveDto(DiscountDto discountDto) {
        Discount discount = new Discount();
        discount.setDis(discountDto.getDis());
        discount.setProducts(getProductsList());
        discount.setStartDate(discountDto.getStartDate());
        discount.setExpiryDate(discountDto.getExpiryDate());
        getRepository().save(discount);
    }

    public void update(DiscountDto discountDto) {
        Discount discount = getRepository().findById(discountDto.getId())
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
        getRepository().save(discount);
    }

    @PostConstruct
    public void init() {
        productsList = new ListsForDiscount();
    }

    public List<Product> getProductsList() {
        return productsList.getProducts();
    }

    public void addToList(Long id) {
        productsList.add(productServiceImpl.findById(id));
    }

    public void removeFromList(Long id) {
        productsList.remove(id);
    }

    public void clearList() {
        productsList.clear();
    }
}
