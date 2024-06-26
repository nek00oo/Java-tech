package ru.itmo.dto;

import lombok.Data;
import ru.itmo.types.Color;

import java.time.LocalDate;

@Data
public class CatDto {
    private String name;
    private LocalDate birthdate;
    private String breed;
    private Color color;
    private Long ownerId;

    @Override
    public String toString() {
        return "{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"birthdate\": \"" + birthdate + "\",\n" +
                "  \"breed\": \"" + breed + "\",\n" +
                "  \"color\": \"" + color + "\",\n" +
                "  \"ownerId\": \"" + ownerId + "\"\n" +
                "}";
    }
}

