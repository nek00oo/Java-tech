package ru.itmo.model.client;

public interface IClient {
    boolean hasCompleteInformation();
    void setAddress(String address);
    void setPassportNumber(String passportNumber);
}
