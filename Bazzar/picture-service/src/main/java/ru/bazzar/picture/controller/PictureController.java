package ru.bazzar.picture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.picture.api.AppError;
import ru.bazzar.picture.api.PictureDto;
import ru.bazzar.picture.api.ResourceNotFoundException;
import ru.bazzar.picture.converters.PictureConverter;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.service.PictureService;

//         http://localhost:8555/tech-bazzar-picture/swagger-ui/index.html
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/picture")
@Tag(name = "Контроллер картинок продуктов", description = "Методы работы с картинками продуктов")
public class PictureController {

    private final PictureService pictureService;
    private final PictureConverter pictureConverter;

    @Operation(
            summary = "Запрос на получение dto картинки продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PictureDto.class))
                    ),
                    @ApiResponse(
                            description = "Картинка не найдена", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public PictureDto findPictureByIdAndReturnDto(
            @PathVariable
            @Parameter(description = "Идентификатор", required = true)
            Long id){
        return pictureConverter.entityToDto(pictureService.findById(id));
    }
    @Operation(
            summary = "Запрос на сохранение dto картинки и возврат id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Long.class))
                    ),
                    @ApiResponse(
                            description = "BAD_REQUEST", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = HttpMessageNotReadableException.class))
                    )
            }
    )
    @PostMapping()
    public Long savePicDtoAndReturnId(
            @RequestBody
            @Parameter(description = "PictureDto Json c id == null", required = true)
            PictureDto pictureDto){
        Picture p = pictureService.save(pictureConverter.dtoToEntity(pictureDto));
        return p.getId();
    }
    @Operation(
            summary = "Запрос на удаление dto картинки продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Картинка не найдена", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deletePic(@PathVariable Long id){
        try {
            pictureService.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("id " + id + " не найден!");
        }

    }

    @Operation(
            hidden = true,
            summary = "Запрос на очистку кэша picture-service",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/evict")
    public void evict(){
        pictureService.cacheEvict();
    }
}
