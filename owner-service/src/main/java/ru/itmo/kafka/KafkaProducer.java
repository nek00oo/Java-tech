package ru.itmo.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.itmo.dto.OwnerDto;

@Service
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void checkOwnerExists(OwnerDto ownerDto) {
        kafkaTemplate.send("check_owner_exists_response", String.valueOf(ownerDto));
    }

    public void getAllOwners(String owners) {
        kafkaTemplate.send("get_all_owners_response", owners);
    }

    public void getOwnerById(OwnerDto ownerDto) {
        kafkaTemplate.send("get_owner_by_id_response", String.valueOf(ownerDto));
    }

    public void deleteOwnerById(OwnerDto ownerDto) {
        kafkaTemplate.send("delete_owner_by_id_response", String.valueOf(ownerDto));
    }
}
