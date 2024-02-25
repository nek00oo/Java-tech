package ru.itmo.entity.accounts;

import lombok.Getter;
import ru.itmo.entity.IFinancialService;
import ru.itmo.model.Client;

@Getter
public class DepositAccount extends Account implements IFinancialService {
    private final double startAmount;

    public DepositAccount(Client owner, Double startAmount) {
        super(owner);
        this.startAmount = startAmount;
        this.balance = startAmount;
    }

    @Override
    public void withdraw(Double amountMoney) {
        System.out.println("Снятие с депозитного счета недоступно до окончания срока");
    }

    @Override
    public void getInterest(Double ratioInterestRate) {
        balance += startAmount * ratioInterestRate;
    }
}