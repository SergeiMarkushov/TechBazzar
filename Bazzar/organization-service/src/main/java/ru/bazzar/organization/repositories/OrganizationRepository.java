package ru.bazzar.organization.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bazzar.organization.entities.Organization;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByTitleIgnoreCase(String title);

    List<Organization> findAllByIsActive(boolean isActive);
}
