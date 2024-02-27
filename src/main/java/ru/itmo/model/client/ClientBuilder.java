package ru.itmo.model.client;

import java.lang.IllegalArgumentException;

/**
 * This class is used to create a {@link Client}
 */
public class ClientBuilder implements IClientBuilder {
    private String firstname;
    private String lastname;
    private String address;
    private String passportNumber;

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
     * @throws IllegalArgumentException If the parameter is not added
     * @return IClient
     */
    @Override
    public IClient build() {
        if (firstname == null)
            throw new IllegalArgumentException("firstname cannot be null");
        if (lastname == null)
            throw new IllegalArgumentException("lastname cannot be null");

        return new Client(
                firstname,
                lastname,
                address,
                passportNumber);
    }
}
