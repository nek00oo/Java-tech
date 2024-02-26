package ru.itmo.entity;

import lombok.Setter;
import ru.itmo.entity.accounts.Account;
import ru.itmo.entity.accounts.CreditAccount;
import ru.itmo.model.Client;

import java.util.ArrayList;
import java.util.List;

public class Bank extends Publisher {
    private long idAccountCounter;
    private final List<Account> accounts;
    private Double maxWithdrawalAmountForQuestionableAccount;
    private Double ratioInterestRate;
    private Double creditLimit;
    private Double commission;

    Bank(Double ratioInterestRate, Double creditLimit, Double maxWithdrawalAmountForQuestionableAccount, Double commission) {
        this.idAccountCounter = 0;
        this.ratioInterestRate = ratioInterestRate;
        this.creditLimit = creditLimit;
        this.commission = commission;
        this.maxWithdrawalAmountForQuestionableAccount = maxWithdrawalAmountForQuestionableAccount;
        accounts = new ArrayList<>();
    }

    public Account createDebitAccount(Client owner) {
        CreditAccount newAccount = new CreditAccount(owner, creditLimit, commission, idAccountCounter++);
        accounts.add(newAccount);
        return newAccount;
    }

    public void processInterest() {
        for (Account account : accounts) {
            if (account instanceof ISavingAccount financialAccount)
                financialAccount.getInterest(ratioInterestRate);
        }
    }

    public void changeRatioInterestRate(Double newInterestRate) {
        notifyObservers("Измение процента на остаток :" + ratioInterestRate + " -> " + newInterestRate, ISavingAccount.class);
        ratioInterestRate = newInterestRate;
    }

    public void changeCreditLimit(Double newCreditLimit) {
        notifyObservers("Новый кредитный лимит :" + creditLimit + " -> " + newCreditLimit, CreditAccount.class);
        creditLimit = newCreditLimit;
    }

    public void withdraw(long idAccount, Double amountMoney){
        accounts.stream()
                .filter(account -> account.getIdAccount() == idAccount)
                .findFirst()
                .ifPresent(account -> account.withdraw(amountMoney));
    }

    public void deposit(long idAccount, Double amountMoney){
        accounts.stream()
                .filter(account -> account.getIdAccount() == idAccount)
                .findFirst()
                .ifPresent(account -> account.deposit(amountMoney));
    }

    public boolean cancellationTransaction(long idAccount, long idTransaction) {
        return accounts.stream()
                .filter(account -> account.getIdAccount() == idAccount)
                .findFirst()
                .map(account -> account.cancellationTransaction(idTransaction))
                .orElse(false);
    }
}
