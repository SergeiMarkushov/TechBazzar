package ru.bazzar.picture.entities;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

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

}