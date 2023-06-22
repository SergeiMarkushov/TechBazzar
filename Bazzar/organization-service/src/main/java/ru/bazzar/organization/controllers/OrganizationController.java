package ru.bazzar.organization.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bazzar.organization.api.AppError;
import ru.bazzar.organization.api.OrganizationDto;
import ru.bazzar.organization.api.ResourceNotFoundException;
import ru.bazzar.organization.converters.OrganizationConverter;
import ru.bazzar.organization.services.OrganizationService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationConverter converter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCompany(@RequestParam("owner") String companyOwner,
                              @RequestParam("name") String companyName,
                              @RequestParam("description") String companyDescription,
                              @RequestParam(value = "companyImage", required = false) MultipartFile companyImage) throws IOException {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setTitle(companyName);
        organizationDto.setDescription(companyDescription);
        organizationService.save(organizationDto, companyOwner, companyImage);
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getByTitle(@PathVariable String title) {
        if ((organizationService.findByTitleIgnoreCase(title)).isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Организация с таким названием не существует"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(converter.entityToDto(organizationService.findByTitleIgnoreCase(title).get()));
    }

    @GetMapping("by_owner")
    public List<OrganizationDto> findAllByOwner(@RequestHeader String username) {
        return organizationService.findAllByOwner(username);
    }

    @GetMapping("/not_confirmed")
    public OrganizationDto notConfirmed() throws ResourceNotFoundException {
        return converter.entityToDto(organizationService.notConfirmed());
    }

    @GetMapping("/confirm/{title}")
    public void confirm(@PathVariable String title) {
        organizationService.confirm(title);
    }

    @GetMapping
    public List<OrganizationDto> findAll() {
        return organizationService.findAll();
    }

    @GetMapping("/bun/{id}")
    public void orgBun(@PathVariable Long id) {
        organizationService.orgBun(id);
    }

    @GetMapping("/is_active/{title}")
    public boolean isOrgActive(@PathVariable String title) {
        return organizationService.isOrgActive(title);
    }
}
