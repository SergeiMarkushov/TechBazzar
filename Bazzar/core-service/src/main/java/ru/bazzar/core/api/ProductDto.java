package ru.bazzar.core.api;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Модель продукта")
public class ProductDto {
    @Schema(description = "Идентификатор продукта", example = "1")
    private Long id;
    @Schema(description = "Название продукта",minLength = 2, maxLength = 100, example = "Груша")
    private String title;
    @Schema(description = "Описание продукта",minLength = 10, maxLength = 1000, example = "Груша сезонная, сорт - Дюшес")
    private String description;
    @Schema(description = "Название организации",minLength = 1, maxLength = 200, example = "Агрокомплекс")
    private String organizationTitle;
    @Schema(description = "The price of the product", format = "decimal")
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
