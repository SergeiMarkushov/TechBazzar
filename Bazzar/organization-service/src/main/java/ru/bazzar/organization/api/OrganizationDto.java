package ru.bazzar.organization.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель организации")
public class OrganizationDto {
    @Schema(description = "Идентификатор организации", example = "1")
    private Long id;
    @Schema(description = "Название организации", maxLength = 100, example = "Агропром")
    private String title;
    @Schema(description = "Описание организации", maxLength = 1000, example = "Продукты питания")
    private String description;
    @Schema(description = "Вледелец организации (email адрес)", maxLength = 100, example = "ivan@mail.ru")
    private String owner;
    @Schema(description = "Статус активности", example = "true")
    private boolean isActive;


    @Override
    public String toString() {
        return "OrganizationDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationDto that = (OrganizationDto) o;
        return isActive == that.isActive && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, owner, isActive);
    }
}
