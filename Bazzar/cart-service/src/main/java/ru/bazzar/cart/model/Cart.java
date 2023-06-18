package ru.bazzar.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.bazzar.cart.api.ProductDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(ProductDto product) {
        if (product.getQuantity() != 0) {
            for (CartItem item :
                    items) {
                if (product.getId().equals(item.getProductId())) {
                    item.changeQuantity(1);
                    recalculate();
                    return;
                }
            }
            items.add(new CartItem(product.getId(), product.getTitle(), 1, product.getPrice(), product.getPrice()));
            recalculate();
        }
    }

    public void remove(Long productId) {
        if (items.removeIf(item -> item.getProductId().equals(productId))) {
            recalculate();
        }
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem item :
                items) {
            totalPrice = totalPrice.add(item.getPrice()).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public void changeQuantity(ProductDto product, Integer delta) {
        for (CartItem item :
                items) {
            if (product.getId().equals(item.getProductId())) {
                item.changeQuantity(delta);
                if (item.getQuantity() == 0) {
                    remove(product.getId());
                    return;
                }
                recalculate();
                return;
            }
        }
        recalculate();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(items, cart.items) && Objects.equals(totalPrice, cart.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, totalPrice);
    }
}
