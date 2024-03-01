package ru.itmo.entity.banks.validate;

public class ValidatorBank implements IValidatorBank{
    @Override
    public void validatePositiveArgument(Double value) {
        if (value <= 0.0)
            throw new IllegalArgumentException("The argument must be positive");
    }
}
