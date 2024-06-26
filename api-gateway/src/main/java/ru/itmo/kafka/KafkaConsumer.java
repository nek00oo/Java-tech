package ru.itmo.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.itmo.dto.CatDto;
import ru.itmo.dto.OwnerDto;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private CompletableFuture<Optional<OwnerDto>> ownerFuture;
    private CompletableFuture<Optional<List<OwnerDto>>> allOwners;
    private CompletableFuture<Optional<OwnerDto>> ownerById;
    private CompletableFuture<Optional<OwnerDto>> deleteOwnerById;
    private CompletableFuture<Optional<List<CatDto>>> allCats;
    private CompletableFuture<Optional<CatDto>> deleteCatById;
    private CompletableFuture<Optional<CatDto>> catById;
    private CompletableFuture<Optional<List<CatDto>>> colorCats;
    private CompletableFuture<Optional<List<CatDto>>> ownerCats;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "check_owner_exists_response", groupId = "group")
    public void checkOwnerExists(String ownerString) {
        if (ownerFuture != null) {
            OwnerDto ownerDto;
            try {
                ownerDto = objectMapper.readValue(ownerString, OwnerDto.class);
                ownerFuture.complete(Optional.ofNullable(ownerDto));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @KafkaListener(topics = "get_all_owners_response", groupId = "group")
    public void getAllOwners(String ownersString) {
        if (allOwners != null) {
            try {
                List<OwnerDto> ownerList = objectMapper.readValue(ownersString, new TypeReference<List<OwnerDto>>() {});
                allOwners.complete(Optional.ofNullable(ownerList));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @KafkaListener(topics = "get_owner_by_id_response", groupId = "group")
    public void getOwnerById(String ownerString) {
        if (ownerById != null) {
            OwnerDto ownerDto;
            try {
                ownerDto = objectMapper.readValue(ownerString, OwnerDto.class);
                ownerById.complete(Optional.ofNullable(ownerDto));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @KafkaListener(topics = "delete_owner_by_id_response", groupId = "group")
    public void deleteOwnerById(String ownerString) {
        if (deleteOwnerById != null) {
            OwnerDto ownerDto;
            try {
                ownerDto = objectMapper.readValue(ownerString, OwnerDto.class);
                deleteOwnerById.complete(Optional.ofNullable(ownerDto));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @KafkaListener(topics = "get_all_cats_response", groupId = "group")
    public void getAllCats(String catsString) {
        if (allCats != null) {
            try {
                List<CatDto> catDtoList = objectMapper.readValue(catsString, new TypeReference<List<CatDto>>() {});
                allCats.complete(Optional.ofNullable(catDtoList));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @KafkaListener(topics = "delete_cat_by_id_response", groupId = "group")
    public void deleteCatById(String catString) {
        if (deleteCatById != null) {
            CatDto catDto;
            try {
                catDto = objectMapper.readValue(catString, CatDto.class);
                deleteCatById.complete(Optional.ofNullable(catDto));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @KafkaListener(topics = "get_cat_by_id_response", groupId = "group")
    public void getCatById(String catString) {
        if (catById != null) {
            CatDto catDto;
            try {
                catDto = objectMapper.readValue(catString, CatDto.class);
                catById.complete(Optional.ofNullable(catDto));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @KafkaListener(topics = "get_cat_by_color_response", groupId = "group")
    public void getCatsByColor(String catsString) {
        if (colorCats != null) {
            try {
                List<CatDto> catDtoList = objectMapper.readValue(catsString, new TypeReference<List<CatDto>>() {});
                colorCats.complete(Optional.ofNullable(catDtoList));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @KafkaListener(topics = "get_cat_list_response", groupId = "group")
    public void getOwnerCats(String catsString) {
        if (ownerCats != null) {
            try {
                List<CatDto> catDtoList = objectMapper.readValue(catsString, new TypeReference<List<CatDto>>() {});
                ownerCats.complete(Optional.ofNullable(catDtoList));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public CompletableFuture<Optional<OwnerDto>> getOwnerFuture() {
        ownerFuture = new CompletableFuture<>();
        return ownerFuture;
    }

    public CompletableFuture<Optional<List<OwnerDto>>> getAllOwners() {
        allOwners = new CompletableFuture<>();
        return allOwners;
    }

    public CompletableFuture<Optional<OwnerDto>> getOwnerById() {
        ownerById = new CompletableFuture<>();
        return ownerById;
    }

    public CompletableFuture<Optional<OwnerDto>> deleteOwnerById() {
        deleteOwnerById = new CompletableFuture<>();
        return deleteOwnerById;
    }

    public CompletableFuture<Optional<List<CatDto>>> getAllCats() {
        allCats = new CompletableFuture<>();
        return allCats;
    }

    public CompletableFuture<Optional<CatDto>> deleteCatById() {
        deleteCatById = new CompletableFuture<>();
        return deleteCatById;
    }

    public CompletableFuture<Optional<CatDto>> getCatById() {
        catById = new CompletableFuture<>();
        return catById;
    }

    public CompletableFuture<Optional<List<CatDto>>> getCatsByColor() {
        colorCats = new CompletableFuture<>();
        return colorCats;
    }

    public CompletableFuture<Optional<List<CatDto>>> getOwnerCats() {
        ownerCats = new CompletableFuture<>();
        return ownerCats;
    }
}
