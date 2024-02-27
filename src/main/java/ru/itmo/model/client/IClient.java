package ru.itmo.model.client;

/**
 * The client's interface, the {@link ClientBuilder} is used to create client class.
 */
public interface IClient {

    /**
     * a method that determines whether a class is not questionable
     *
     * @return True if the client is reliable and false in other
     */
    boolean hasCompleteInformation();

    void setAddress(String address);

    void setPassportNumber(String passportNumber);
}
