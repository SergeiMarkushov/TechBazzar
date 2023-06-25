package ru.bazzar.core.entities;

import jdk.jfr.BooleanFlag;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Column(name = "address")
    @Size(min = 2, max = 200, message = "Поле address должно быть в диапазоне от {min} до {max} символов.")
    private String address;

    @Column(name = "phone")
    @Size(min = 2, max = 100, message = "Поле phone должно быть в диапазоне от {min} до {max} символов.")//паттерн?
    private String phone;

    @Column(name = "total_price")
    @Digits(integer=8, fraction=2, message = "Поле total_price должно соответствовать формату: {integer} знаков до, и {fraction} знаков после точки (денежный формат).")
    private BigDecimal totalPrice;

    @Column(name = "status")
    @NotNull(message = "status is null")
    @BooleanFlag
    private boolean status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    @Override
//    public String toString() {
//        return "Order{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", items=" + items +
//                ", address='" + address + '\'' +
//                ", phone='" + phone + '\'' +
//                ", totalPrice=" + totalPrice +
//                ", status=" + status +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
//                '}';
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return status == order.status && Objects.equals(id, order.id) && Objects.equals(username, order.username) && Objects.equals(items, order.items) && Objects.equals(address, order.address) && Objects.equals(phone, order.phone) && Objects.equals(totalPrice, order.totalPrice) && Objects.equals(createdAt, order.createdAt) && Objects.equals(updatedAt, order.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, items, address, phone, totalPrice, status, createdAt, updatedAt);
    }
}
