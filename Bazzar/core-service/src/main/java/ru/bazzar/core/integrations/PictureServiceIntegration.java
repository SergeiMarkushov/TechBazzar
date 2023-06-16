package ru.bazzar.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.bazzar.core.api.PictureDto;
import ru.bazzar.core.api.ResourceNotFoundException;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PictureServiceIntegration {
    private final WebClient pictureServiceWebClient;

    public PictureDto getPictureDtoById(Long id) {
        return pictureServiceWebClient.get()
                .uri("api/v1/picture/find-pic-dto/" + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.BAD_REQUEST.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Ресурс не найден."))
                )
                .bodyToMono(PictureDto.class)
                .block();
    }

    public Long savePictureDtoAndReturnId(PictureDto pictureDto){
        return pictureServiceWebClient.post()
                .uri("api/v1/picture/save-dto-return-id")
                .body(BodyInserters.fromValue(pictureDto))
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    public void deletePictureById(Long id) {
         pictureServiceWebClient.delete()
                .uri("api/v1/picture/delete/" + id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
