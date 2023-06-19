package ru.bazzar.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private String productTitle;
    private Long orderId;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto that = (OrderItemDto) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(productTitle, that.productTitle) && Objects.equals(orderId, that.orderId) && Objects.equals(pricePerProduct, that.pricePerProduct) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productTitle, orderId, quantity, pricePerProduct, price);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "id=" + id +
                ", productTitle='" + productTitle + '\'' +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                ", costPerProduct=" + pricePerProduct +
                ", cost=" + price +
                '}';
    }
}
