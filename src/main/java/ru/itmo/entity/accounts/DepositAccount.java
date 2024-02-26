package ru.itmo.entity.accounts;

import lombok.Getter;
import ru.itmo.entity.ISavingAccount;
import ru.itmo.entity.Transaction;
import ru.itmo.model.Client;
import ru.itmo.type.OperationType;

import java.util.Date;

@Getter
public class DepositAccount extends Account implements ISavingAccount {
    private final double startAmount;
    private Date dateEndTerm;

    public DepositAccount(Client owner, Double startAmount, long idAccount, Date dateEndTerm) {
        super(owner, idAccount);
        this.dateEndTerm = dateEndTerm;
        this.startAmount = startAmount;
        this.balance = startAmount;
    }


    @Override
    public void withdraw(Double amountMoney) {
        balance -= amountMoney;
        transactions.add(new Transaction(idTransactionCounter++, getIdAccount(), amountMoney, new OperationType.Withdraw()));
    }

    @Override
    public void getInterest(Double ratioInterestRate) {
        balance += startAmount * ratioInterestRate;
    }
}