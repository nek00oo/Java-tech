package ru.itmo.model.client.validate;

public class ValidatorClient implements IValidatorClient{

    @Override
    public void validateFirstname(String firstname) {
        validate(firstname, "^[a-zA-Z]+$", "Invalid characters in the firstname");
    }

    @Override
    public void validateLastname(String lastname) {
        validate(lastname, "^[a-zA-Z]+$", "Invalid characters in the lastname");
    }

    @Override
    public void validateAddress(String address) {
        validate(address, "^[a-zA-Z0-9\\s,.-]+$", "Invalid characters in the address");
    }

    @Override
    public void validatePassportNumber(String passportNumber) {
        validate(passportNumber, "^[a-zA-Z0-9]+$", "Invalid characters in the passportNumber");
    }

    private void validate(String value, String validPattern, String errorMessage) {
        if (!value.matches(validPattern)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
