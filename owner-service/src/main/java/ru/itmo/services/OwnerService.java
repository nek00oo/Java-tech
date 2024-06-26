package ru.itmo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.contracts.IOwnerService;
import ru.itmo.dto.OwnerDto;
import ru.itmo.models.Owner;
import ru.itmo.repositories.IOwnerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OwnerService implements IOwnerService {

    private final IOwnerRepository ownerRepository;

    private final OwnerMapper ownerMapper;

    @Override
    public OwnerDto addOwner(OwnerDto ownerDto) {
        ownerRepository.save(ownerMapper.dtoToOwner(ownerDto));

        return ownerDto;
    }

    @Override
    public OwnerDto deleteOwnerById(Long ownerId) {
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if (owner.isEmpty())
            return null;

        ownerRepository.deleteById(ownerId);
        return ownerMapper.ownerToDto(owner.get());
    }

    @Override
    public OwnerDto findOwnerById(Long ownerId){
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if (owner.isEmpty())
            return null;
        return ownerMapper.ownerToDto(owner.get());
    }

    @Override
    public List<OwnerDto> findAllOwners(){
        return ownerRepository
                .findAll()
                .stream()
                .map(ownerMapper::ownerToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OwnerDto checkOwnerExists(String name){
        Optional<Owner> owner = ownerRepository.findByName(name);
        if (owner.isEmpty())
            return null;
        return ownerMapper.ownerToDto(owner.get());
    }
}
