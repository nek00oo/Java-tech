package ru.itmo.entity.banks;

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
    private final List<Bank> banks;

    public CentralBank() {
        banks = new ArrayList<>();
    }

    /**
     * @param ratioInterestRate                         the interest rate ratio
     * @param creditLimit                               credit limit for accounts in this bank
     * @param maxWithdrawalAmountForQuestionableAccount maximum withdrawal amount for doubtful accounts
     * @param commission                                commission rate
     * @return Bank
     */
    public Bank CreateBank(Double ratioInterestRate, Double creditLimit, Double maxWithdrawalAmountForQuestionableAccount, Double commission) {
        Bank newBank = new Bank(ratioInterestRate, creditLimit, maxWithdrawalAmountForQuestionableAccount, commission);
        banks.add(newBank);
        return newBank;
    }

    /**
     * A method for notifying banks of the need to charge interest
     */
    public void notifyInterest() {
        for (Bank bank : banks) {
            bank.processInterest();
        }
    }

}
