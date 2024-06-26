package ru.itmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itmo.models.Cat;

import java.util.List;

public interface ICatRepository extends JpaRepository<Cat, Long> {
    @Query("SELECT c FROM Cat c WHERE CAST(c.color AS string) = :color")
    List<Cat> findByColor(@Param("color") String color);

    @Query("SELECT c FROM Cat c WHERE c.owner.id = :ownerId")
    List<Cat> findByOwnerId(@Param("ownerId") Long ownerId);
}

