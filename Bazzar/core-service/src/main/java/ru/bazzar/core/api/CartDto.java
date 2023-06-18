package ru.bazzar.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private List<CartItemDto> items;
    private BigDecimal totalPrice;

    @Override
    public String toString() {
        return "CartDto{" +
                "items=" + items +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDto = (CartDto) o;
        return Objects.equals(items, cartDto.items) && Objects.equals(totalPrice, cartDto.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, totalPrice);
    }
}
