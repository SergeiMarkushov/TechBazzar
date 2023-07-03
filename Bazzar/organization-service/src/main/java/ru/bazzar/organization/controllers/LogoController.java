package ru.bazzar.organization.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bazzar.organization.api.AppError;
import ru.bazzar.organization.api.ResourceNotFoundException;
import ru.bazzar.organization.entities.Logo;
import ru.bazzar.organization.services.OrganizationService;

import java.io.ByteArrayInputStream;

//         http://localhost:8185/tech-bazzar-organization/swagger-ui/index.html

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/logo")
@Tag(name = "Контроллер логотипов организаций", description = "Методы работы с логотипами организаций")
public class LogoController {
    private final OrganizationService organizationService;
    @Operation(
            summary = "Запрос на получение ResponseEntity<?> логотипа по названию организации",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
                    ),
                    @ApiResponse(
                            description = "Логотип не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{title}")
    public ResponseEntity<?> getLogoByTitleOrganization(
            @PathVariable
            @Parameter(description = "В строке запроса указывается название существующей организации", required = true)
            String title) {
        try {
            Logo logo = organizationService.findLogoByTitleOrganization(title);
            return ResponseEntity.ok()
                    .header("fileName", logo.getOriginalFileName())
                    .contentType(MediaType.valueOf(logo.getContentType()))
                    .contentLength(logo.getSize())
                    .body(new InputStreamResource(new ByteArrayInputStream(logo.getBytes())));
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND
            );
        }
    }
}
