package ru.itmo.entity.accounts;

import ru.itmo.entity.ISubscriber;
import ru.itmo.model.Client;

import java.util.ArrayList;

public abstract class Account implements ISubscriber {
    protected Client owner;
    protected Double balance;

    private final ArrayList<String> messages;

    public Account(Client owner) {
        this.owner = owner;
        this.balance = 0.0;
        messages = new ArrayList<>();
    }

    public void deposit(Double amountMoney) {
        balance += amountMoney;
        System.out.println("Пополнение на сумму: " + amountMoney);
    }

    @Override
    public void update(String message){
        messages.add("received notification: " + message);
    }

    public abstract void withdraw(Double amount);
}