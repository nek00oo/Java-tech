package ru.itmo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.dto.CatDto;
import ru.itmo.models.Cat;

@Service
@RequiredArgsConstructor
public class CatMapper {

    public Cat dtoToCat(CatDto catDto) {
        Cat cat = new Cat();

        cat.setName(catDto.getName());
        cat.setBreed(catDto.getBreed());
        cat.setColor(catDto.getColor());
        cat.setBirthDate(catDto.getBirthdate());

        return cat;
    }

    public CatDto catToDto(Cat cat) {
        CatDto catDto = new CatDto();

        catDto.setName(cat.getName());
        catDto.setBreed(cat.getBreed());
        catDto.setColor(cat.getColor());
        catDto.setBirthdate(cat.getBirthDate());
        if (cat.getOwner() != null)
            catDto.setOwnerId(cat.getOwner().getId());

        return catDto;
    }
}
