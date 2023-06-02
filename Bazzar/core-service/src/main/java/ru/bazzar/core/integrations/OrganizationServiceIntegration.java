package ru.bazzar.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.bazzar.core.api.OrganizationDto;
import ru.bazzar.core.api.ResourceNotFoundException;

@Component
@RequiredArgsConstructor
public class OrganizationServiceIntegration {
    private final WebClient organizationServiceWebClient;

    public OrganizationDto getOrganizationByTitle(String title) {
        return organizationServiceWebClient.get()
                .uri("api/v1/organizations/" + title)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.BAD_REQUEST.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Организация не найдена в organization-service."))
                )
                .bodyToMono(OrganizationDto.class)
                .block();

    }

    public boolean isOrgActive(String title) {
        return Boolean.TRUE.equals(organizationServiceWebClient.get()
                .uri("api/v1/organizations/is_active/" + title)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Произошла непредвиденная ошибка."))
                )
                .bodyToMono(Boolean.class)
                .block());
    }
}
