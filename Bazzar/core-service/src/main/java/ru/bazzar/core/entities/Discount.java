package ru.bazzar.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "discount")
    private List<Product> products;

    @Column(name = "dis")
    private int dis;//уточнить правильный тип данных

    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDateTime startDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", products=" + products +
                ", dis=" + dis +
                ", startDate=" + startDate +
                ", expiryDate=" + expiryDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return dis == discount.dis && Objects.equals(id, discount.id) && Objects.equals(products, discount.products) && Objects.equals(startDate, discount.startDate) && Objects.equals(expiryDate, discount.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products, dis, startDate, expiryDate);
    }
}
