package ru.itmo.entity.accounts;

import ru.itmo.model.Client;

public class CreditAccount extends Account {
    private double creditLimit;
    private final double commission;

    public CreditAccount(Client owner, Double creditLimit, Double commission) {
        super(owner);
        this.creditLimit = creditLimit;
        this.commission = commission;
    }

    @Override
    public void withdraw(Double amountMoney) {
        if (amountMoney <= balance){
            balance -= amountMoney;
        }
        else if (amountMoney <= balance + creditLimit){
            double remain = amountMoney - balance;
            balance = 0.0;
            creditLimit -= remain + remain * commission;
        }
        else {
            System.out.println("Недостаточно средств для снятия");
        }
    }
}
