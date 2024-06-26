package ru.itmo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OwnerDto {
    private String name;
    private LocalDate birthdate;
    private String password;
    private String roles;

    @Override
    public String toString() {
        return "{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"birthdate\": \"" + birthdate + "\",\n" +
                "  \"password\": \"" + password + "\",\n" +
                "  \"roles\": \"" + roles + "\"\n" +
                "}";
    }
}