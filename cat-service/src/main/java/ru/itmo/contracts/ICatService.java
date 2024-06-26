package ru.itmo.contracts;

import ru.itmo.dto.CatDto;
import ru.itmo.models.Cat;
import ru.itmo.models.Owner;

import java.util.List;
import java.util.Optional;

public interface ICatService {

    CatDto addCat(CatDto catDto);

    CatDto deleteCatById(Long catId);

    void makeFriends(Long cat1Id, Long cat2Id);

    List<CatDto> findCatByOwner(Long ownerId);

    void addOwnerToCat(Long catId, Owner owner);

    List<CatDto> findCatByColor(String color);

    CatDto findCatById(Long catId);

    List<CatDto> findAllCats();
}

