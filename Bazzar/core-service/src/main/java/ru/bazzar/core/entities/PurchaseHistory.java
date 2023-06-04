package ru.bazzar.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
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
}
