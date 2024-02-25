package ru.itmo.entity.accounts;

import ru.itmo.entity.IFinancialService;
import ru.itmo.model.Client;


public class DebitAccount extends Account implements IFinancialService {

    private Double accumulatedInterest = 0.0;

    public DebitAccount(Client owner) {
        super(owner);
    }

    @Override
    public void withdraw(Double amountMoney) {
        if (amountMoney <= balance) {
            balance -= amountMoney;
            System.out.println("Снятие на сумму: " + amountMoney);
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
