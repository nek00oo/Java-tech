package ru.itmo.services;

import org.springframework.stereotype.Service;
import ru.itmo.dto.OwnerDto;
import ru.itmo.models.Owner;

@Service
public class OwnerMapper {
    public Owner dtoToOwner(OwnerDto ownerDto) {
        Owner owner = new Owner();

        owner.setRoles(ownerDto.getRoles());
        owner.setName(ownerDto.getName());
        owner.setPassword(ownerDto.getPassword());
        owner.setBirthDate(ownerDto.getBirthdate());

        return owner;
    }

    public OwnerDto ownerToDto(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();

        ownerDto.setName(owner.getName());
        ownerDto.setRoles(owner.getRoles());
        ownerDto.setPassword(owner.getPassword());
        ownerDto.setBirthdate(owner.getBirthDate());

        return ownerDto;
    }
}
