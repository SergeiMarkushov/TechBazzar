package ru.bazzar.picture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bazzar.picture.entities.Picture;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Optional<Picture> findByFileName(String fileName);
}
