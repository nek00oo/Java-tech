package ru.itmo.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.itmo.contracts.IOwnerService;
import ru.itmo.dto.OwnerDto;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final IOwnerService ownerService;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "check_owner_exists_request", groupId = "group")
    public void checkOwnerExists(String name) {
        OwnerDto ownerDto = ownerService.checkOwnerExists(name);

        if (ownerDto != null) {
            kafkaProducer.checkOwnerExists(ownerDto);
        }
    }

    @KafkaListener(topics = "create_owner_request", groupId = "group")
    public void createOwner(String ownerString) {
        OwnerDto ownerDto;
        try {
            ownerDto = objectMapper.readValue(ownerString, OwnerDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ownerService.addOwner(ownerDto);
    }

    @KafkaListener(topics = "get_all_owners_request", groupId = "group")
    public void getAllOwners() {
        StringBuilder owners = new StringBuilder();

        List<OwnerDto> ownerList = ownerService.findAllOwners();

        for (OwnerDto ownerDto : ownerList) {
            owners.append(ownerDto.toString());
        }

        String ans = "[" + owners.toString().replace("}{", "}\n,{") + "]";

        kafkaProducer.getAllOwners(ans);
    }

    @KafkaListener(topics = "get_owner_by_id_request", groupId = "group")
    public void getOwnerById(String id) {
        OwnerDto ownerDto = ownerService.findOwnerById(Long.valueOf(id));

        if (ownerDto != null) {
            kafkaProducer.getOwnerById(ownerDto);
        }
    }

    @KafkaListener(topics = "delete_owner_by_id_request", groupId = "group")
    public void deleteOwnerById(String id) {
        OwnerDto ownerDto = ownerService.deleteOwnerById(Long.valueOf(id));

        if (ownerDto != null) {
            kafkaProducer.deleteOwnerById(ownerDto);
        }
    }
}
