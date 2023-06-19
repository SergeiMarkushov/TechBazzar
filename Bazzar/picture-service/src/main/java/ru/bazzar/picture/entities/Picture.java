package ru.bazzar.picture.entities;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    @Size(min = 2, max = 100, message = "Поле title должно быть в диапазоне от {min} до {max} символов.")
    //@Unique
    private String fileName;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
//    @Size(max = 5 * 1024 * 1024, message = "Размер не должен превышать 5 Mb")
    private byte[] bytes;

    @Override
    public String toString() {
        return "Picture{" +
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
        Picture picture = (Picture) o;
        return Objects.equals(id, picture.id) && Objects.equals(fileName, picture.fileName) && Objects.equals(contentType, picture.contentType) && Arrays.equals(bytes, picture.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, fileName, contentType);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}