package ru.itmo.model.client;

import lombok.Getter;

/**
 * The client's class, the {@link ClientBuilder} is used to create it
 */

@Getter
public class Client implements IClient {
    private final String firstName;
    private final String lastName;
    private String address;
    private String passportNumber;

    Client(String firstName, String lastName, String address, String passportNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.passportNumber = passportNumber;
    }

    /**
     * a method that determines whether a class is not questionable
     * @return boolean
     */
    public boolean hasCompleteInformation() {
        return address != null && passportNumber != null;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

}