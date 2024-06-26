package ru.itmo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.itmo.dto.CatDto;
import ru.itmo.kafka.KafkaConsumer;
import ru.itmo.kafka.KafkaProducer;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/cats")
@AllArgsConstructor
public class CatController {
    private final KafkaProducer kafkaProducer;
    private final KafkaConsumer kafkaConsumer;

    @GetMapping
    public ResponseEntity<List<CatDto>> findAllCats() {
        kafkaProducer.getAllCats();
        Optional<List<CatDto>> catDtos;
        try {
            catDtos = kafkaConsumer.getAllCats().get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to retrieve owner information", e);
        }

        return ResponseEntity.ok(catDtos.get());
    }

    @GetMapping("{catId}")
    public ResponseEntity<CatDto> findCatById(@PathVariable Long catId) {
        kafkaProducer.getCatById(catId);
        Optional<CatDto> catDto;
        try {
            catDto = kafkaConsumer.getCatById().get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to retrieve owner information", e);
        }

        return ResponseEntity.ok(catDto.get());
    }

    @GetMapping("color/{color}")
    public ResponseEntity<List<CatDto>> findCatByColor(@PathVariable String color) {
        kafkaProducer.getCatsByColor(color);
        Optional<List<CatDto>> catDtos;
        try {
            catDtos = kafkaConsumer.getCatsByColor().get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to retrieve owner information", e);
        }

        return ResponseEntity.ok(catDtos.get());
    }

    @GetMapping("findByOwner/{ownerId}")
    public ResponseEntity<List<CatDto>> findCatByOwner(@PathVariable Long ownerId) {
        kafkaProducer.getOwnerCats(ownerId);
        Optional<List<CatDto>> catDtos;
        try {
            catDtos = kafkaConsumer.getOwnerCats().get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to retrieve owner information", e);
        }

        return ResponseEntity.ok(catDtos.get());
    }

    //TODO добавить обработку, у кота могут быть переданы неправильные параметры
    @PostMapping
    public ResponseEntity<CatDto> addCat(@RequestBody CatDto catDto) {
        kafkaProducer.createCat(catDto);

        return ResponseEntity.ok(catDto);
    }

/*    @PostMapping("{catId}/addOwner")
    public ResponseEntity<String> addOwnerToCat(@PathVariable Long catId, @RequestBody Owner owner) {
        try {
            catService.addOwnerToCat(catId, owner);
            return ResponseEntity.ok("Owner added to cat successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("{cat1Id}/{cat2Id}/make_friends")
    public ResponseEntity<String> makeFriends(@PathVariable Long cat1Id, @PathVariable Long cat2Id) {
        try {
            catService.makeFriends(cat1Id, cat2Id);
            return ResponseEntity.ok("Cat friendship created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }*/

    @DeleteMapping("{id}")
    public ResponseEntity<CatDto> deleteCat(@PathVariable("id") Long catId) {
        kafkaProducer.deleteCatById(catId);
        Optional<CatDto> catDto;
        try {
            catDto = kafkaConsumer.deleteCatById().get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to retrieve owner information", e);
        }

        return ResponseEntity.ok(catDto.get());
    }
}
