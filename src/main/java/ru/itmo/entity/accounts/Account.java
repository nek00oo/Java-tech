package ru.itmo.entity.accounts;

import lombok.Getter;
import ru.itmo.entity.ISubscriber;
import ru.itmo.entity.Transaction;
import ru.itmo.model.Client;
import ru.itmo.type.OperationType;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Account implements ISubscriber {
    private final long idAccount;
    protected long idTransactionCounter;
    protected Client owner;
    protected Double balance;

    protected final List<Transaction> transactions;
    protected final List<String> messages;

    public Account(Client owner, long idAccount) {
        this.owner = owner;
        this.idAccount = idAccount;
        this.balance = 0.0;
        idTransactionCounter = 0;
        messages = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public void deposit(Double amountMoney) {
        transactions.add(new Transaction(idTransactionCounter++, idAccount, amountMoney, new OperationType.Deposit()));
        balance += amountMoney;
        System.out.println("Пополнение на сумму: " + amountMoney);
    }

    public boolean cancellationTransaction(long idTransaction) {
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

    public abstract void withdraw(Double amount);

    @Override
    public void update(String message) {
        messages.add("received notification: " + message);
    }
}