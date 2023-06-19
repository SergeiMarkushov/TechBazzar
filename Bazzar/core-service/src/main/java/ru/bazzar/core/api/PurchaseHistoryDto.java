package ru.bazzar.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryDto {
    private Long id;
    private String email;
    private String productTitle;
    private String organizationTitle;
    private int quantity;
    private String datePurchase;

    @Override
    public String toString() {
        return "PurchaseHistoryDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", productTitle='" + productTitle + '\'' +
                ", organizationTitle='" + organizationTitle + '\'' +
                ", quantity=" + quantity +
                ", datePurchase='" + datePurchase + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseHistoryDto that = (PurchaseHistoryDto) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(productTitle, that.productTitle) && Objects.equals(organizationTitle, that.organizationTitle) && Objects.equals(datePurchase, that.datePurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, productTitle, organizationTitle, quantity, datePurchase);
    }
}
