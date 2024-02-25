package ru.itmo.entity;

import ru.itmo.entity.accounts.Account;

import java.util.ArrayList;
import java.util.List;

public class Bank extends Publisher {
    private final List<Account> accounts;
    private Double maxWithdrawalAmountForQuestionableAccount;
    private Double ratioInterestRate;
    private Double creditLimit;

    Bank(Double ratioInterestRate, Double creditLimit, Double maxWithdrawalAmountForQuestionableAccount){
        this.ratioInterestRate = ratioInterestRate;
        this.creditLimit = creditLimit;
        this.maxWithdrawalAmountForQuestionableAccount = maxWithdrawalAmountForQuestionableAccount;
        accounts = new ArrayList<>();
    }

    public void AddAccount(Account account) {
        accounts.add(account);
    }

    public void processInterest(){
        for (Account account : accounts){
            if (account instanceof IFinancialService financialAccount)
                financialAccount.getInterest(ratioInterestRate);
        }
    }

    public void changeRatioInterestRate(Double newInterestRate){
        notifyObservers("Измение процента на остаток :" + ratioInterestRate + " -> " + newInterestRate);
        ratioInterestRate = newInterestRate;
    }

    public void changeCreditLimit(Double newCreditLimit){
        notifyObservers("Новый кредитный лимит :" + creditLimit + " -> " + newCreditLimit);
        creditLimit = newCreditLimit;
    }
}
