package ru.bazzar.picture.api;

import lombok.*;

import java.util.Arrays;
import java.util.Objects;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureDto {

    private Long id;
    private String fileName;
    private byte[] bytes;
    private String contentType;

    @Override
    public String toString() {
        return "PictureDto{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                ", contentType='" + contentType + '\'' +
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
