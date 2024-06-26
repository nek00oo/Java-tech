package ru.itmo.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.itmo.dto.CatDto;
import ru.itmo.dto.OwnerDto;


@Service
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void checkOwnerExists(String name) {
        kafkaTemplate.send("check_owner_exists_request", name);
    }

    public void createOwner(OwnerDto ownerDto) {
        kafkaTemplate.send("create_owner_request", String.valueOf(ownerDto));
    }

    public void getAllOwners() {
        kafkaTemplate.send("get_all_owners_request", "");
    }

    public void getOwnerById(Long id) {
        kafkaTemplate.send("get_owner_by_id_request", String.valueOf(id));
    }

    public void deleteOwnerById(Long id) {
        kafkaTemplate.send("delete_owner_by_id_request", String.valueOf(id));
    }

    public void getAllCats() {
        kafkaTemplate.send("get_all_cats_request", "");
    }

    public void createCat(CatDto catDto) {
        kafkaTemplate.send("create_cat_request", String.valueOf(catDto));
    }

    public void deleteCatById(Long id) {
        kafkaTemplate.send("delete_cat_by_id_request", String.valueOf(id));
    }

    public void getCatById(Long id) {
        kafkaTemplate.send("get_cat_by_id_request", String.valueOf(id));
    }

    public void getCatsByColor(String color) {
        kafkaTemplate.send("get_cat_by_color_request", color);
    }

    public void getOwnerCats(Long id) {
        kafkaTemplate.send("get_cat_list_request", String.valueOf(id));
    }
}
