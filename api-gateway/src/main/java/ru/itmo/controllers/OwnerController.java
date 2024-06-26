package ru.itmo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.dto.OwnerDto;
import ru.itmo.kafka.KafkaConsumer;
import ru.itmo.kafka.KafkaProducer;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/owners")
@AllArgsConstructor
public class OwnerController {

    private final KafkaConsumer kafkaConsumer;
    private final KafkaProducer kafkaProducer;

    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<OwnerDto>> findAllOwners() {
        kafkaProducer.getAllOwners();
        Optional<List<OwnerDto>> ownersDto;
        try {
            ownersDto = kafkaConsumer.getAllOwners().get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to retrieve owner information", e);
        }
        return ResponseEntity.ok(ownersDto.get());
    }

    @GetMapping("{ownerId}")
    public ResponseEntity<OwnerDto> findOwnerById(@PathVariable Long ownerId) {
        kafkaProducer.getOwnerById(ownerId);
        Optional<OwnerDto> ownerDto;
        try {
            ownerDto = kafkaConsumer.getOwnerById().get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to retrieve owner information", e);
        }

        return ResponseEntity.ok(ownerDto.get());
    }

    //TODO добавить обработку, могут быть переданы некоректные данные
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerDto> addOwner(@RequestBody OwnerDto owner) {
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        kafkaProducer.createOwner(owner);

        return ResponseEntity.ok(owner);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<OwnerDto> deleteOwner(@PathVariable("id") Long ownerId) {
        kafkaProducer.deleteOwnerById(ownerId);
        Optional<OwnerDto> ownerDto;
        try {
            ownerDto = kafkaConsumer.deleteOwnerById().get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to retrieve owner information", e);
        }

        return ResponseEntity.ok(ownerDto.get());
    }
}
