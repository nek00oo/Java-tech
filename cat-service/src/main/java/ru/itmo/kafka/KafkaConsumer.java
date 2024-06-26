package ru.itmo.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.itmo.contracts.ICatService;
import ru.itmo.dto.CatDto;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumer {
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;
    private final ICatService catService;

    @KafkaListener(topics = "get_all_cats_request", groupId = "group")
    public void getAllCats() {
        StringBuilder cats = new StringBuilder();

        List<CatDto> catDtoList = catService.findAllCats();

        for (CatDto catDto : catDtoList) {
            cats.append(catDto.toString());
        }

        String ans = "[" + cats.toString().replace("}{", "}\n,{") + "]";

        kafkaProducer.getAllCats(ans);
    }

    @KafkaListener(topics = "create_cat_request", groupId = "group")
    public void createCat(String catString) {
        CatDto catDto;
        try {
            catDto = objectMapper.readValue(catString, CatDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        catService.addCat(catDto);
    }

    @KafkaListener(topics = "delete_cat_by_id_request", groupId = "group")
    public void deleteCatById(String id) {
        CatDto catDto = catService.findCatById(Long.valueOf(id));
        catService.deleteCatById(Long.valueOf(id));

        if (catDto != null) {
            kafkaProducer.deleteCatById(catDto);
        }
    }

    @KafkaListener(topics = "get_cat_by_id_request", groupId = "group")
    public void getCatById(String id) {
        CatDto catDto = catService.findCatById(Long.valueOf(id));

        if (catDto != null) {
            kafkaProducer.getCatById(catDto);
        }
    }

    @KafkaListener(topics = "get_cat_by_color_request", groupId = "group")
    public void getCatsByColor(String color) {
        StringBuilder cats = new StringBuilder();

        List<CatDto> catDtoList = catService.findCatByColor(color);

        for (CatDto catDto : catDtoList) {
            cats.append(catDto.toString());
        }

        String ans = "[" + cats.toString().replace("}{", "}\n,{") + "]";

        kafkaProducer.getCatsByColor(ans);
    }

    @KafkaListener(topics = "get_cat_list_request", groupId = "group")
    public void getOwnerCats(String id) {
        StringBuilder cats = new StringBuilder();

        List<CatDto> catDtoList = catService.findCatByOwner(Long.valueOf(id));

        for (CatDto catDto : catDtoList) {
            cats.append(catDto.toString());
        }

        String ans = "[" + cats.toString().replace("}{", "}\n,{") + "]";

        kafkaProducer.getOwnerCats(ans);
    }
}
