package ru.itmo.entity.accounts;

import lombok.Getter;
import lombok.NonNull;
import ru.itmo.entity.ISubscriber;
import ru.itmo.model.client.IClient;
import ru.itmo.model.Transaction;
import ru.itmo.type.OperationType;

import java.util.ArrayList;
import java.util.List;

/**
 * The account class, with standard operations, implements the ISubscriber {@link ISubscriber} interface
 * for subscribing to the bank.
 * To createt this class, use {@link ru.itmo.entity.banks.Bank}
 *
 * @author valer
 * @version 1.0
 * @since 2024-02-27
 */
@Getter
public abstract class Account implements ISubscriber {
    private final Long idAccount;
    protected Long idTransactionCounter;
    protected IClient owner;
    protected Double balance;

    protected final List<Transaction> transactions;
    protected final List<String> messages;

    Account(@NonNull IClient owner, Long idAccount) {
        this.owner = owner;
        this.idAccount = idAccount;
        this.balance = 0.0;
        idTransactionCounter = 0L;
        messages = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    /**
     * the method for replenishing the account balance
     *
     * @param amountMoney the amount of money to replenish
     * @return True if the deposit was successful, false otherwise.
     */
    public boolean deposit(Double amountMoney) {
        transactions.add(new Transaction(idTransactionCounter++, idAccount, amountMoney, new OperationType.Deposit()));
        balance += amountMoney;
        return true;
    }

    /**
     * the method of transferring money between accounts
     *
     * @param recipient   the one to whom the money is being sent
     * @param amountMoney the amount of money to transfer
     * @return True if the transfer was successful, false otherwise.
     */
    public boolean transfer(Account recipient, Double amountMoney) {
        if (balance < amountMoney)
            return false;

        if (withdraw(amountMoney)) {
            recipient.deposit(amountMoney);
            transactions.add(new Transaction(idTransactionCounter++, idAccount, amountMoney, new OperationType.Transfer(recipient.getIdAccount())));
            return true;
        }

        return false;
    }

    /**
     * the method for canceling the transaction
     *
     * @param idTransaction id of the transaction to cancel
     * @return Transaction if the transaction cancellation was successful, null otherwise.
     */
    public Transaction cancellationTransaction(Long idTransaction) {
        return transactions.stream()
                .filter(transaction -> transaction.idTransaction() == idTransaction)
                .findFirst()
                .map(transaction -> {
                    transactions.remove(transaction);
                    return transaction;
                })
                .orElse(null);
    }

    @Override
    public void update(String message) {
        messages.add("received notification: " + message);
    }

    /**
     * Withdraw money from a specified account.
     *
     * @param amountMoney The amount of money to withdraw.
     * @return True if the withdrawal was successful, false otherwise.
     */
    public abstract boolean withdraw(Double amountMoney);
}