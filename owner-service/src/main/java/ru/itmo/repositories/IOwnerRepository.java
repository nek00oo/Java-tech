package ru.itmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.models.Owner;

import java.util.Optional;

public interface IOwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByName(String username);
}