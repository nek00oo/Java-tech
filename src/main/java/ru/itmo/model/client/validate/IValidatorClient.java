package ru.itmo.model.client.validate;

public interface IValidatorClient {
    void validateFirstname(String firstname);
    void validateLastname(String lastname);
    void validateAddress(String address);
    void validatePassportNumber(String passportNumber);
}
