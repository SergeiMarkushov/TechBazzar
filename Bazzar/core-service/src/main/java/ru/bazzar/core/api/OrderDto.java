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
public class OrderDto {
    // TODO: 27.05.2023 если сюда добавить имэйл о организацию, то от PurchaseHistory можно избавиться
    private Long id;
    private String username;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
    private String createdAt;
    private boolean status;

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", items=" + items +
                ", totalPrice=" + totalPrice +
                ", createdAt='" + createdAt + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return status == orderDto.status && Objects.equals(id, orderDto.id) && Objects.equals(username, orderDto.username) && Objects.equals(items, orderDto.items) && Objects.equals(totalPrice, orderDto.totalPrice) && Objects.equals(createdAt, orderDto.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, items, totalPrice, createdAt, status);
    }
}
