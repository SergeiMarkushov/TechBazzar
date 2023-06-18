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
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private String description;
    private String organizationTitle;
    private BigDecimal price;
    private int quantity;
    private boolean isConfirmed;
    private List<CharacteristicDto> characteristicsDto;
    private Long pictureId;

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", organizationTitle='" + organizationTitle + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", isConfirmed=" + isConfirmed +
                ", characteristicsDto=" + characteristicsDto +
                ", pictureId=" + pictureId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return quantity == that.quantity && isConfirmed == that.isConfirmed && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(organizationTitle, that.organizationTitle) && Objects.equals(price, that.price) && Objects.equals(characteristicsDto, that.characteristicsDto) && Objects.equals(pictureId, that.pictureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, organizationTitle, price, quantity, isConfirmed, characteristicsDto, pictureId);
    }
}
