package ru.bazzar.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "quantity")
    @Min(value = 1, message = "Минимальное значение поля quantity: {value}.")
    private int quantity;

    @Column(name = "price_per_product")
    @Digits(integer=8, fraction=2, message = "Поле price_per_product должно соответствовать формату: {integer} знаков до, и {fraction} знаков после точки (денежный формат).")
    private BigDecimal pricePerProduct;

    @Column(name = "price")
    @Digits(integer=8, fraction=2, message = "Поле price должно соответствовать формату: {integer} знаков до, и {fraction} знаков после точки (денежный формат).")
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    @Override
//    public String toString() {
//        return "OrderItem{" +
//                "id=" + id +
//                ", product=" + product +
//                ", order=" + order +
//                ", quantity=" + quantity +
//                ", pricePerProduct=" + pricePerProduct +
//                ", price=" + price +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
//                '}';
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.quantity && Objects.equals(id, orderItem.id) && Objects.equals(product, orderItem.product) && Objects.equals(order, orderItem.order) && Objects.equals(pricePerProduct, orderItem.pricePerProduct) && Objects.equals(price, orderItem.price) && Objects.equals(createdAt, orderItem.createdAt) && Objects.equals(updatedAt, orderItem.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, order, quantity, pricePerProduct, price, createdAt, updatedAt);
    }
}
