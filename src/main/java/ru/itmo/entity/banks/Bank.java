package ru.itmo.entity.banks;

import lombok.NonNull;
import ru.itmo.entity.IInterestReceivable;
import ru.itmo.entity.Publisher;
import ru.itmo.entity.accounts.Account;
import ru.itmo.entity.accounts.CreditAccount;
import ru.itmo.entity.accounts.DebitAccount;
import ru.itmo.entity.accounts.DepositAccount;
import ru.itmo.model.Transaction;
import ru.itmo.model.client.IClient;
import ru.itmo.type.OperationType;

import java.util.*;

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

public class Bank extends Publisher implements IBank {
    private Long idAccountCounter;
    private final List<Account> accounts;
    private final Double maxWithdrawalAmountForQuestionableAccount;
    private Double ratioInterestRate;
    private Double creditLimit;
    private final Double commission;

    /**
     * @param ratioInterestRate                         the interest rate ratio
     * @param creditLimit                               credit limit for accounts in this bank
     * @param maxWithdrawalAmountForQuestionableAccount maximum withdrawal amount for doubtful accounts
     * @param commission                                commission rate
     * @throws IllegalArgumentException if the passed parameter is incorrect
     */
    Bank(Double ratioInterestRate, Double creditLimit, Double maxWithdrawalAmountForQuestionableAccount, Double commission) {
        this.idAccountCounter = 0L;
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
    public CreditAccount createCreditAccount(@NonNull IClient owner) {
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
    public DebitAccount createDebitAccount(@NonNull IClient owner) {
        DebitAccount newAccount = new DebitAccount(owner, idAccountCounter++);
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
    public DepositAccount createDepositAccount(@NonNull IClient owner, Double startAmountMoney, Date dateEndTerm) {
        DepositAccount newAccount = new DepositAccount(owner, startAmountMoney, idAccountCounter++, dateEndTerm);
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
     * @param newCreditLimit A new credit limit is being set
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
    public boolean withdraw(Long idAccount, Double amountMoney) {
        return accounts.stream()
                .filter(account -> account.getIdAccount().equals(idAccount))
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
    public boolean deposit(Long idAccount, Double amountMoney) {
        return accounts.stream()
                .filter(account -> account.getIdAccount().equals(idAccount))
                .findFirst()
                .map(account -> account.deposit(amountMoney))
                .orElse(false);
    }

    @Override
    public boolean transfer(Long idSenderAccount, Long idRecipientAccount, Double amountMoney) {
        Optional<Account> senderOptional = findAccountById(idSenderAccount);
        Optional<Account> recipientOptional = findAccountById(idRecipientAccount);

        if (senderOptional.isPresent() && recipientOptional.isPresent()) {
            Account sender = senderOptional.get();
            Account recipient = recipientOptional.get();

            return sender.transfer(recipient, amountMoney);
        }


        return false;
    }

    /**
     * The method of canceling the operation
     *
     * @param idAccount     ID of the account to cancel the transaction in
     * @param idTransaction ID of the transaction
     * @return True if the transaction cancellation was successful, false otherwise.
     */
    @Override
    public boolean cancellationTransaction(Long idAccount, Long idTransaction) {
        return accounts.stream()
                .filter(account -> account.getIdAccount().equals(idAccount))
                .findFirst()
                .map(account -> {
                    Transaction transaction = account.cancellationTransaction(idTransaction);
                    if (transaction.operationType() instanceof OperationType.Transfer transfer){
                        deposit(idAccount, transaction.amountMoney());
                        withdraw(transfer.getIdRecipient(), transaction.amountMoney());
                        return true;
                    } else if (transaction.operationType() instanceof OperationType.Withdraw) {
                       deposit(idAccount, transaction.amountMoney());
                       return true;
                    } else if (transaction.operationType() instanceof OperationType.Deposit){
                        withdraw(idAccount, transaction.amountMoney());
                    }
                    return null;
                })
                .orElse(false);
    }

    public Optional<Account> findAccountById(Long accountId) {
        return accounts.stream()
                .filter(account -> account.getIdAccount().equals(accountId))
                .findFirst();
    }
}
