package ru.bazzar.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.bazzar.core.api.PictureDto;

@Component
@RequiredArgsConstructor
public class PictureServiceIntegration {
    private final WebClient pictureServiceWebClient;

    public PictureDto getPictureDtoById(Long id) {
        return pictureServiceWebClient.get()
                .uri("api/v1/picture/" + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.BAD_REQUEST.value(),
                        clientResponse -> Mono.error(new NumberFormatException("Требуется ввести число"))
                )
                .bodyToMono(PictureDto.class)
                .block();
    }

    public Long savePictureDtoAndReturnId(PictureDto pictureDto){
        return pictureServiceWebClient.post()
                .uri("api/v1/picture")
                .body(BodyInserters.fromValue(pictureDto))
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    public void deletePictureById(Long id) {
         pictureServiceWebClient.delete()
                .uri("api/v1/picture/" + id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
