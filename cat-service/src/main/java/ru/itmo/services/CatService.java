package ru.itmo.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import ru.itmo.contracts.ICatService;
import ru.itmo.dto.CatDto;
import ru.itmo.models.Cat;
import ru.itmo.models.Owner;
import ru.itmo.repositories.ICatRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CatService implements ICatService {

    private final ICatRepository catRepository;

    private final CatMapper catMapper;

    @Override
    public CatDto addCat(CatDto catDto) {
        catRepository.save(catMapper.dtoToCat(catDto));

        return catDto;
    }

    @Override
    public CatDto deleteCatById(Long catId) {
        Optional<Cat> cat = catRepository.findById(catId);
        if (cat.isEmpty())
            return null;
        catRepository.deleteById(catId);
        return catMapper.catToDto(cat.get());
    }

    @Override
    public void makeFriends(Long cat1Id, Long cat2Id) {
        Cat cat1 = catRepository.findById(cat1Id).orElseThrow(() -> new EntityNotFoundException("Cat with id " + cat1Id + " not found"));
        Cat cat2 = catRepository.findById(cat2Id).orElseThrow(() -> new EntityNotFoundException("Cat with id " + cat2Id + " not found"));

        cat1.addFriend(cat2);
        cat2.addFriend(cat1);

        catRepository.save(cat1);
        catRepository.save(cat2);
    }

    @Override
    public List<CatDto> findCatByOwner(Long ownerId) {
        return catRepository
                .findByOwnerId(ownerId)
                .stream()
                .map(catMapper::catToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addOwnerToCat(Long catId, Owner owner) {
        Cat cat = catRepository.findById(catId).orElseThrow(() -> new RuntimeException("Cat with id " + catId + " not found"));
        owner.addCat(cat);
        catRepository.save(cat);
    }

    @Override
    public List<CatDto> findCatByColor(String color) {
        return catRepository
                .findByColor(color)
                .stream()
                .map(catMapper::catToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CatDto findCatById(Long catId) {
        Optional<Cat> cat = catRepository.findById(catId);
        if (cat.isEmpty())
            return null;

        return catMapper.catToDto(cat.get());
    }

    @Override
    @PostFilter("hasRole('USER') ? (filterObject.owner != null ? filterObject.owner.user.name == principal.username : false) : true")
    public List<CatDto> findAllCats() {
        return catRepository
                .findAll()
                .stream()
                .map(catMapper::catToDto)
                .collect(Collectors.toList());
    }
}

