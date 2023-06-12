package ru.bazzar.picture.api;

import lombok.*;
import java.nio.file.Path;


@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PictureDto {

    private Long id;
    private String fileName;
    private byte[] bytes;
    private String contentType;

}
