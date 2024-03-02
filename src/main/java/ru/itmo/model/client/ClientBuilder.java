package ru.itmo.model.client;

import ru.itmo.model.client.validate.IValidatorClient;

import java.lang.IllegalArgumentException;

/**
 * This class is used to create a {@link Client}
 */
public class ClientBuilder implements IClientBuilder {
    private String firstname;
    private String lastname;
    private String address;
    private String passportNumber;

    private IValidatorClient validatorClient;

    public ClientBuilder() {}

    public ClientBuilder(IValidatorClient validatorClient){
        this.validatorClient = validatorClient;
    }

    @Override
    public IClientBuilder addFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    @Override
    public IClientBuilder addLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    @Override
    public IClientBuilder addAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public IClientBuilder addPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    /**
     * method that creates the {@link Client}, the parameters first and last name are required
     *
     * @return IClient
     */
    @Override
    public IClient build() {
        if (validatorClient != null){
            try {
                validatorClient.validateFirstname(firstname);
                validatorClient.validateLastname(lastname);
                if (address != null)
                    validatorClient.validateAddress(address);
                if (passportNumber != null)
                    validatorClient.validatePassportNumber(passportNumber);
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
                return null;
            }
        }

        return new Client(
                firstname,
                lastname,
                address,
                passportNumber);
    }
}
