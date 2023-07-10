package ru.bazzar.organization.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bazzar.organization.api.AppError;
import ru.bazzar.organization.api.LogoSaverException;
import ru.bazzar.organization.api.OrganizationDto;
import ru.bazzar.organization.api.ResourceNotFoundException;
import ru.bazzar.organization.converters.OrganizationConverter;
import ru.bazzar.organization.services.OrganizationService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

//         http://localhost:8185/tech-bazzar-organization/swagger-ui/index.html

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Контроллер организаций", description = "Методы работы с организациями")
@RequestMapping("/api/v1/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationConverter converter;

    @Operation(
            summary = "Запрос на получение ResponseEntity<?> (DTO) организации",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrganizationDto.class))),
                    @ApiResponse(
                            description = "Организация с таким названием не найдена", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{title}")
    public ResponseEntity<?> getByTitle(
            @PathVariable @Parameter(description = "Название организации", required = true) String title) {
        try {
            return ResponseEntity.ok(converter.entityToDto(organizationService.findByTitleIgnoreCase(title)));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Запрос на получение List<OrganizationDto> всех имеющихся организаций",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Успешное получение списка организаций",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrganizationDto.class))))
            }
    )
    @GetMapping
    public List<OrganizationDto> findAll() {
        return organizationService.findAll();
    }

    @Operation(
            summary = "Запрос на получение List<OrganizationDto> всех имеющихся организаций владельца (username)",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Успешное получение списка организаций",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrganizationDto.class))))
            }
    )
    @GetMapping("/by_owner")
    public List<OrganizationDto> findAllByOwner(
            @RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        System.out.println(username);
        return organizationService.findAllByOwner(username);
    }

    @Operation(summary = "Проверить активность организации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Организация активна",
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "404", description = "Организация не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class)))})
    @GetMapping("/is_active/{title}")
    public ResponseEntity<?> isOrgActive(
            @PathVariable @Parameter(description = "Название организации", required = true) String title) {
        try {
            Boolean isActive = organizationService.isOrgActive(title);
            return ResponseEntity.ok()
                    .body(isActive);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Получить неподтвержденные организации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ - получена неподтвержденная организация",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrganizationDto.class))),
            @ApiResponse(responseCode = "404", description = "Нет неподтвержденных организаций",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class)))})
    @GetMapping("/not_confirmed")
    public ResponseEntity<?> notConfirmed() {
        try {
            OrganizationDto notConfirmedOrganizationDto = converter.entityToDto(organizationService.notConfirmed());
            return ResponseEntity.ok()
                    .body(notConfirmedOrganizationDto);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Выполнить операцию orgBun для организации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешный ответ"),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class)))})
    @PutMapping("/bun/{id}")
    public ResponseEntity<?> orgBun(@PathVariable @Parameter(description = "Идентификатор", required = true) Long id) {
        try {
            organizationService.orgBun(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Подтвердить организацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class)))})
    @PutMapping("/confirm/{title}")
    public ResponseEntity<?> confirm(
            @PathVariable @Parameter(description = "Название организации", required = true) String title) {
        try {
            organizationService.confirm(title);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Создать организацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешное создание"),
            @ApiResponse(responseCode = "400", description = "Ошибка при создании организации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class)))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCompany(
            @RequestParam("owner") @Parameter(description = "Владелец организации", required = true) String companyOwner,
            @RequestParam("name") @Parameter(description = "Название организации", required = true) String companyName,
            @RequestParam("description") @Parameter(description = "Описание организации", required = true) String companyDescription,
            @RequestParam(value = "companyImage")
            @Parameter(description = "Изображение организации",
                    required = true, content = @Content(mediaType = "multipart/form-data")) MultipartFile companyImage) {
        try {
            OrganizationDto organizationDto = new OrganizationDto();
            organizationDto.setTitle(companyName);
            organizationDto.setDescription(companyDescription);
            organizationService.save(organizationDto, companyOwner, companyImage);
            return ResponseEntity.ok().build();
        } catch (LogoSaverException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/titles")
    public ResponseEntity<?> getOrgNames() {
        List<String> orgNames = organizationService.getOrganizationTitles();
        return ResponseEntity.ok().body(orgNames);
    }
}
