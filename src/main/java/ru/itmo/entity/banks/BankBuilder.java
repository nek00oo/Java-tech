package ru.itmo.entity.banks;

import ru.itmo.entity.banks.validate.IValidatorBank;

public class BankBuilder implements IBankBuilder {
    private Double ratioInterestRate;
    private Double creditLimit;
    private Double maxWithdrawalAmountForQuestionableAccount;
    private Double commission;

    private IValidatorBank validatorBank;

    public BankBuilder() {}

    public BankBuilder(IValidatorBank validatorBank){
        this.validatorBank = validatorBank;
    }

    @Override
    public IBankBuilder addRatioInterestRate(Double ratioInterestRate) {
        this.ratioInterestRate = ratioInterestRate;
        return this;
    }

    @Override
    public IBankBuilder addCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
        return this;
    }

    @Override
    public IBankBuilder addMaxWithdrawalAmountForQuestionableAccount(Double maxWithdrawalAmountForQuestionableAccount) {
        this.maxWithdrawalAmountForQuestionableAccount = maxWithdrawalAmountForQuestionableAccount;
        return this;
    }

    @Override
    public IBankBuilder addCommission(Double commission) {
        this.commission = commission;
        return this;
    }

    @Override
    public IBank build() {
        if (validatorBank != null){
            try {
                validatorBank.validatePositiveArgument(ratioInterestRate);
                validatorBank.validatePositiveArgument(creditLimit);
                validatorBank.validatePositiveArgument(maxWithdrawalAmountForQuestionableAccount);
                validatorBank.validatePositiveArgument(commission);
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                return null;
            }
        }

        return new Bank(
                ratioInterestRate,
                creditLimit,
                maxWithdrawalAmountForQuestionableAccount,
                commission);
    }

}
