package ru.bazzar.cart.api;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;


    @Override
    public String toString() {
        return "CartItemDto{" +
                "productId=" + productId +
                ", productTitle='" + productTitle + '\'' +
                ", quantity=" + quantity +
                ", pricePerProduct=" + pricePerProduct +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemDto that = (CartItemDto) o;
        return quantity == that.quantity && Objects.equals(productId, that.productId) && Objects.equals(productTitle, that.productTitle) && Objects.equals(pricePerProduct, that.pricePerProduct) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productTitle, quantity, pricePerProduct, price);
    }
}
