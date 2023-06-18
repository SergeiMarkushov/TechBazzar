package ru.bazzar.cart.api;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacteristicDto {
    private Long id;
    private String name;
    private ProductDto productDto;

    @Override
    public String toString() {
        return "CharacteristicDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productDto=" + productDto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacteristicDto that = (CharacteristicDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(productDto, that.productDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, productDto);
    }
}
