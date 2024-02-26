package ru.itmo.entity.accounts;

import ru.itmo.entity.Transaction;
import ru.itmo.model.Client;
import ru.itmo.type.OperationType;

public class CreditAccount extends Account {
    private double creditLimit;
    private final double commission;

    public CreditAccount(Client owner, Double creditLimit, Double commission, long idAccount) {
        super(owner, idAccount);
        this.creditLimit = creditLimit;
        this.commission = commission;
    }

    @Override
    public void withdraw(Double amountMoney) {
        if (amountMoney > balance + creditLimit) {
            System.out.println("Недостаточно средств для снятия");
            return;
        }
        if (amountMoney <= balance) {
            balance -= amountMoney;
        } else if (amountMoney <= balance + creditLimit) {
            double remain = amountMoney - balance;
            balance = 0.0;
            creditLimit -= remain + remain * commission;
        }
        transactions.add(new Transaction(idTransactionCounter++, getIdAccount(), amountMoney, new OperationType.Withdraw()));
    }
}
