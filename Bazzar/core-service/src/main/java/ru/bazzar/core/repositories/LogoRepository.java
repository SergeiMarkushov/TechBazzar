package ru.bazzar.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bazzar.core.entities.Logo;

@Repository
public interface LogoRepository extends JpaRepository<Logo, Long> {
}
