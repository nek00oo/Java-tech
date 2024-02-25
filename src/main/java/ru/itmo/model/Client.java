package ru.itmo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
public class Client {
    private final String firstName;
    private final String lastName;
    @Setter
    private String address;
    @Setter
    private String passportNumber;

    public boolean hasCompleteInformation() {
        return address != null && passportNumber != null;
    }

}
