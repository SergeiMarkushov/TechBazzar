package ru.bazzar.core.api;

import lombok.*;


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
