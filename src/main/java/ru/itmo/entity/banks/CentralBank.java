package ru.itmo.entity.banks;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The class of the central bank, through which banks are created.
 * It also notifies banks that interest needs to be accrued.
 *
 * @author valer
 * @version 1.0
 * @since 2024-02-27
 */
public class CentralBank {
    private final List<IBank> banks;
    @Setter
    private IBankBuilder bankBuilder;

    public CentralBank(IBankBuilder bankBuilder)
    {
        this.bankBuilder = bankBuilder;
        banks = new ArrayList<>();
    }

    /**
     * @param ratioInterestRate                         the interest rate ratio
     * @param creditLimit                               credit limit for accounts in this bank
     * @param maxWithdrawalAmountForQuestionableAccount maximum withdrawal amount for doubtful accounts
     * @param commission                                commission rate
     * @return Bank
     */
    public IBank CreateBank(Double ratioInterestRate, Double creditLimit, Double maxWithdrawalAmountForQuestionableAccount, Double commission) {
        IBank newBank = bankBuilder.addRatioInterestRate(ratioInterestRate)
                .addCreditLimit(creditLimit)
                .addMaxWithdrawalAmountForQuestionableAccount(maxWithdrawalAmountForQuestionableAccount)
                .addCommission(commission).build();
        banks.add(newBank);
        return newBank;
    }


    /**
     * A method for notifying banks of the need to charge interest
     */
    public void notifyInterest() {
        for (IBank bank : banks) {
            bank.processInterest();
        }
    }

}
