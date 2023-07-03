package ru.bazzar.picture.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Arrays;
import java.util.Objects;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель кртинки продукта")
public class PictureDto {
    // TODO: 27.06.2023 required = true - пока не проставлял - есть вопросы надо ли...
    @Schema(description = "Идентификатор кртинки продукта", example = "1")
    private Long id;
    @Schema(description = "Имя файла", maxLength = 100, example = "pic.jpeg")
    private String fileName;
    @Schema(description = "Тип контента", maxLength = 50, example = "image/jpeg")
    private String contentType;
    @Schema(description = "Массив байтов")
    private byte[] bytes;

    @Override
    public String toString() {
        return "PictureDto{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureDto that = (PictureDto) o;
        return Objects.equals(id, that.id) && Objects.equals(fileName, that.fileName) && Arrays.equals(bytes, that.bytes) && Objects.equals(contentType, that.contentType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, fileName, contentType);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}
