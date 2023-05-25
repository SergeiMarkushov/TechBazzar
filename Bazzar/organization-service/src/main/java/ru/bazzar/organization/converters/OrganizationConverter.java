package ru.bazzar.organization.converters;

import org.springframework.stereotype.Service;
import ru.bazzar.api.OrganizationDto;
import ru.bazzar.organization.entities.Organization;

@Service
public class OrganizationConverter {
    public Organization dtoToEntity(OrganizationDto dto) {
        return Organization.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }

    public OrganizationDto entityToDto(Organization organization) {
        OrganizationDto dto = new OrganizationDto();
        dto.setId(organization.getId());
        dto.setTitle(organization.getTitle());
        dto.setDescription(organization.getDescription());
        dto.setOwner(organization.getOwner());
        dto.setActive(organization.isActive());
        return dto;
    }
}
