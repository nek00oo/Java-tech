package ru.itmo.contracts;

import ru.itmo.dto.OwnerDto;
import ru.itmo.models.Owner;

import java.util.List;

public interface IOwnerService {
    OwnerDto addOwner(OwnerDto ownerDto);

    OwnerDto deleteOwnerById(Long ownerId);

    OwnerDto findOwnerById(Long ownerId);

    List<OwnerDto> findAllOwners();

    OwnerDto checkOwnerExists(String name);
}
