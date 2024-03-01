package ru.itmo.model.client;

import ru.itmo.model.client.validate.IValidatorClient;

public interface IClientBuilder {
    IClientBuilder addFirstname(String firstname);
    IClientBuilder addLastname(String lastname);
    IClientBuilder addAddress(String address);
    IClientBuilder addPassportNumber(String passportNumber);
    IClient build();

}
