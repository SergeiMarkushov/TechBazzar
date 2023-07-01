package ru.bazzar.core.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;//присваивается БД,

    @Column(name = "title")
    @Size(min = 2, max = 100, message = "Поле title должно быть в диапазоне от {min} до {max} символов.")
    private String title;

    @Column(name = "description")
    @Size(min = 10, max = 1000, message = "Поле description должно быть в диапазоне от {min} до {max} символов.")
    private String description;

    @Column(name = "organization_title")
    @Size(min = 1, max = 200, message = "Поле organization_title должно быть в диапазоне от {min} до {max} символов.")
    private String organizationTitle;

    @Column(name = "price")
    @Digits(integer=8, fraction=2, message = "Поле price должно соответствовать формату: {integer} знаков до, и {fraction} знаков после точки (денежный формат).")
    private BigDecimal price;

    @Column(name = "quantity")
    @Min(value = 0, message = "Поле quantity не может быть отрицательным")
    private int quantity;

    @Column(name = "is_confirmed")
    @NotNull(message = "Поле is_confirmed должно быть NotNull.")
    @BooleanFlag
    private boolean isConfirmed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.EAGER)
    private List<Characteristic> characteristics;

    @Column(name = "picture_id")
    @Min(value = 0, message = "Поле pictureId должно быть больше 0")
    private Long pictureId;

    /*
    - Ключевых слов;
    - Таблицы характеристик;
    */


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity && isConfirmed == product.isConfirmed && Objects.equals(id, product.id) && Objects.equals(title, product.title) && Objects.equals(description, product.description) && Objects.equals(organizationTitle, product.organizationTitle) && Objects.equals(price, product.price) && Objects.equals(discount, product.discount) && Objects.equals(reviews, product.reviews) && Objects.equals(characteristics, product.characteristics) && Objects.equals(pictureId, product.pictureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, organizationTitle, price, quantity, isConfirmed, discount, reviews, characteristics, pictureId);
    }
}
