package ru.bazzar.picture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bazzar.picture.entities.Picture;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    // запрещенный id
    Long FORBIDDEN_ID = 1L;

    @Override
    default void deleteAll() {
        throw new UnsupportedOperationException("Операция по удалению всех сущностей в БД - заблокирована!");
    }

    @Override
    default void deleteById(Long id) {
        if (id.equals(FORBIDDEN_ID)) {
            throw new UnsupportedOperationException("Удаление id = " + FORBIDDEN_ID + " - заблокировано!");
        }
        findById(id).ifPresent(this::delete);
    }


    Optional<Picture> findByFileName(String fileName);

}
