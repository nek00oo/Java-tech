package ru.itmo.entity.accounts;

import ru.itmo.entity.ISavingAccount;
import ru.itmo.entity.Transaction;
import ru.itmo.model.Client;
import ru.itmo.type.OperationType;


public class DebitAccount extends Account implements ISavingAccount {

    private Double accumulatedInterest = 0.0;

    public DebitAccount(Client owner, long idAccount) {
        super(owner, idAccount);
    }

    @Override
    public void withdraw(Double amountMoney) {
        if (amountMoney <= balance) {
            balance -= amountMoney;
            System.out.println("Снятие на сумму: " + amountMoney);
            transactions.add(new Transaction(idTransactionCounter++, getIdAccount(), amountMoney, new OperationType.Withdraw()));
        } else {
            System.out.println("Недостаточно средств на счете");
        }
    }

    //TODO эта функция должна вызываться каждый день
    @Override
    public void getInterest(Double ratioInterestRate) {
        accumulatedInterest += balance * ratioInterestRate;
    }

    //TODO вызывается в конце месяца
    public void getAccumulatedInterest() {
        balance += accumulatedInterest;
    }
}
