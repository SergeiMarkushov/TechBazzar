package ru.bazzar.picture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bazzar.picture.entities.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    @Override
    default void deleteAll() {
        throw new UnsupportedOperationException("Операция по удалению всех сущностей в БД - заблокирована!");
    }

}
