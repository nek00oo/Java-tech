package ru.itmo.entity.banks;

import ru.itmo.entity.IInterestReceivable;
import ru.itmo.entity.Publisher;
import ru.itmo.entity.accounts.Account;
import ru.itmo.entity.accounts.CreditAccount;
import ru.itmo.entity.accounts.DebitAccountI;
import ru.itmo.entity.accounts.DepositAccountI;
import ru.itmo.model.client.IClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The bank class, through which accounts are created, and interaction with accounts is performed.
 * Inherits the Publisher class {@link Publisher}, accounts can subscribe to alerts from the bank.
 * In case of an "erroneous transaction", it is possible to cancel the transaction.
 * To create this class, use {@link CentralBank}
 *
 * @author valer
 * @version 1.0
 * @since 2024-02-27
 */

public class Bank extends Publisher implements IAccountCreatable, IFinancialOperationManager {
    private long idAccountCounter;
    private final List<Account> accounts;
    private Double maxWithdrawalAmountForQuestionableAccount;
    private Double ratioInterestRate;
    private Double creditLimit;
    private Double commission;

    /**
     * @param ratioInterestRate                         the interest rate ratio
     * @param creditLimit                               credit limit for accounts in this bank
     * @param maxWithdrawalAmountForQuestionableAccount maximum withdrawal amount for doubtful accounts
     * @param commission                                commission rate
     * @throws IllegalArgumentException if the passed parameter is incorrect
     */
    Bank(Double ratioInterestRate, Double creditLimit, Double maxWithdrawalAmountForQuestionableAccount, Double commission) {
        //TODO вынести проверки в отдельный класс ?
        if (maxWithdrawalAmountForQuestionableAccount <= 0)
            throw new IllegalArgumentException("The withdrawal amount must be positive");
        if (commission <= 0)
            throw new IllegalArgumentException("The commission must be positive");
        if (ratioInterestRate < 0)
            throw new IllegalArgumentException("The interest rate must not be negative");
        if (creditLimit < 0)
            throw new IllegalArgumentException("The credit limit must be positive");

        this.idAccountCounter = 0;
        this.ratioInterestRate = ratioInterestRate;
        this.creditLimit = creditLimit;
        this.commission = commission;
        this.maxWithdrawalAmountForQuestionableAccount = maxWithdrawalAmountForQuestionableAccount;
        accounts = new ArrayList<>();
    }

    /**
     * Creating a credit account
     *
     * @param owner the bank's client
     * @return CreditAccount
     */
    @Override
    public CreditAccount createCreditAccount(IClient owner) {
        CreditAccount newAccount = new CreditAccount(owner, creditLimit, commission, idAccountCounter++);
        accounts.add(newAccount);
        return newAccount;
    }

    /**
     * Creating a debit account
     *
     * @param owner the bank's client
     * @return DebitAccount
     */
    @Override
    public DebitAccountI createDebitAccount(IClient owner) {
        DebitAccountI newAccount = new DebitAccountI(owner, idAccountCounter++);
        accounts.add(newAccount);
        return newAccount;
    }

    /**
     * Creating a deposit account
     *
     * @param owner            the bank's client
     * @param startAmountMoney the starting amount of money
     * @param dateEndTerm      account expiration date
     * @return DepositAccount
     */
    @Override
    public DepositAccountI createDepositAccount(IClient owner, Double startAmountMoney, Date dateEndTerm) {
        DepositAccountI newAccount = new DepositAccountI(owner, startAmountMoney, idAccountCounter++, dateEndTerm);
        accounts.add(newAccount);
        return newAccount;
    }

    /**
     * Accrual of interest on the account
     */
    @Override
    public void processInterest() {
        for (Account account : accounts) {
            if (account instanceof IInterestReceivable financialAccount)
                financialAccount.accrueInterest(ratioInterestRate);
        }
    }

    /**
     * the method changes the interest rate and notifies the customers for whom it is valid
     *
     * @param newInterestRate new interest rate
     */
    @Override
    public void changeRatioInterestRate(Double newInterestRate) {
        notifyObservers("Changing the percentage to the balance :" + ratioInterestRate + " -> " + newInterestRate, IInterestReceivable.class);
        ratioInterestRate = newInterestRate;
    }

    /**
     * the method changes the credit limit and notifies the customers for whom it is valid
     *
     * @param newCreditLimit
     */
    @Override
    public void changeCreditLimit(Double newCreditLimit) {
        notifyObservers("New credit limit :" + creditLimit + " -> " + newCreditLimit, CreditAccount.class);
        creditLimit = newCreditLimit;
    }

    /**
     * Withdrawing money from the client's account
     *
     * @param idAccount   ID of the account to perform the operation with
     * @param amountMoney The amount of money
     * @return True if the withdrawal was successful, false otherwise.
     */
    @Override
    public boolean withdraw(long idAccount, Double amountMoney) {
        return accounts.stream()
                .filter(account -> account.getIdAccount() == idAccount)
                .findFirst()
                .map(account -> {
                    if (!account.getOwner().hasCompleteInformation() && amountMoney > maxWithdrawalAmountForQuestionableAccount)
                        return false;

                    return account.withdraw(amountMoney);
                })
                .orElse(false);
    }

    /**
     * Replenishment of the bank client's account
     *
     * @param idAccount   ID of the account to perform the operation with
     * @param amountMoney The amount of money
     * @return True if the deposit was successful, false otherwise.
     */
    @Override
    public boolean deposit(long idAccount, Double amountMoney) {
        return accounts.stream()
                .filter(account -> account.getIdAccount() == idAccount)
                .findFirst()
                .map(account -> account.deposit(amountMoney))
                .orElse(false);
    }

    /**
     * The method of canceling the operation
     *
     * @param idAccount     ID of the account to cancel the transaction in
     * @param idTransaction ID of the transaction
     * @return True if the transaction cancellation was successful, false otherwise.
     */
    @Override
    public boolean cancellationTransaction(long idAccount, long idTransaction) {
        return accounts.stream()
                .filter(account -> account.getIdAccount() == idAccount)
                .findFirst()
                .map(account -> account.cancellationTransaction(idTransaction))
                .orElse(false);
    }
}
