package ru.itmo.entity.accounts;

import lombok.Getter;
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

    Account(IClient owner, Long idAccount) {
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
     * the method for canceling the transaction
     *
     * @param idTransaction id of the transaction to cancel
     * @return True if the transaction cancellation was successful, false otherwise.
     */
    public boolean cancellationTransaction(Long idTransaction) {
        return transactions.stream()
                .filter(transaction -> transaction.idTransaction() == idTransaction)
                .findFirst()
                .map(transaction -> {
                    if (transaction.operationType().equals(new OperationType.Transfer())) {
                        balance += transaction.amountMoney();
                    }
                    transactions.remove(transaction);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Withdraw money from a specified account.
     *
     * @param amountMoney The amount of money to withdraw.
     * @return True if the withdrawal was successful, false otherwise.
     */
    public abstract boolean withdraw(Double amountMoney);

    @Override
    public void update(String message) {
        messages.add("received notification: " + message);
    }
}