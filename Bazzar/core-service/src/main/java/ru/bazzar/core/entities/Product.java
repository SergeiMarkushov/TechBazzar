package ru.bazzar.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;//присваивается БД,

    @Column(name = "title")
    @Size(min = 2, max = 100)
    private String title;

    @Column(name = "description")
    @Size(min = 10, max = 1000)
    private String description;

    @Column(name = "organization_title")
    @Size(min = 1, max = 200)
    private String organizationTitle;

    @Column(name = "price")
    @Digits(integer=8, fraction=2)
    private BigDecimal price;

    @Column(name = "quantity")
    @Min(0)//вопрос-может 1???
    private int quantity;

    @Column(name = "is_confirmed")
    @NotNull
    private boolean isConfirmed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id")
    private Discount discount;
    //отзывы и оценки вынесены в класс Review
    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;

    /*
    - Ключевых слов;
    - Таблицы характеристик;
    */

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", organizationTitle='" + organizationTitle + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", isConfirmed=" + isConfirmed +
                ", discount=" + discount +
                ", review=" + review +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganizationTitle() {
        return organizationTitle;
    }

    public void setOrganizationTitle(String organizationTitle) {
        this.organizationTitle = organizationTitle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
