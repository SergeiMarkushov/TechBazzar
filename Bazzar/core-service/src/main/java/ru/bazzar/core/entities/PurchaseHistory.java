package ru.bazzar.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_history")
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "product_title")
    @Size(min = 2, max = 100, message = "Поле product_title должно быть в диапазоне от {min} до {max} символов.")
    private String productTitle;

    @Column(name = "organization")
    @Size(min = 1, max = 200, message = "Поле organization должно быть в диапазоне от {min} до {max} символов.")
    private String organization;

    @Column(name = "quantity")
    @Min(value = 1, message = "Минимальное значение поля quantity: {value}." )
    private int quantity;

    @CreationTimestamp
    @Column(name = "date_purchase", nullable = false, updatable = false)
    private LocalDateTime datePurchase;

    @Override
    public String toString() {
        return "PurchaseHistory{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", productTitle='" + productTitle + '\'' +
                ", organization='" + organization + '\'' +
                ", quantity=" + quantity +
                ", datePurchase=" + datePurchase +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseHistory that = (PurchaseHistory) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(productTitle, that.productTitle) && Objects.equals(organization, that.organization) && Objects.equals(datePurchase, that.datePurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, productTitle, organization, quantity, datePurchase);
    }
}
