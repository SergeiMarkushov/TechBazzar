package ru.bazzar.organization.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bazzar.organization.entities.Logo;

@Repository
public interface LogoRepository extends JpaRepository<Logo, Long> {
}
