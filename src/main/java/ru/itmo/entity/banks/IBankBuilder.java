package ru.itmo.entity.banks;

import ru.itmo.entity.banks.validate.IValidatorBank;

public interface IBankBuilder {
    IBankBuilder addRatioInterestRate(Double ratioInterestRate);
    IBankBuilder addCreditLimit(Double creditLimit);
    IBankBuilder addMaxWithdrawalAmountForQuestionableAccount(Double maxWithdrawalAmountForQuestionableAccount);
    IBankBuilder addCommission(Double commission);
    IBank build();
}
