package ru.itmo.entity;

import java.util.ArrayList;
import java.util.List;

public class CentralBank {
    private final List<Bank> banks;

    CentralBank(){
        banks = new ArrayList<>();
    }

    public Bank CreateBank(Double ratioInterestRate, Double creditLimit, Double maxWithdrawalAmountForQuestionableAccount){
        Bank newBank = new Bank(ratioInterestRate, creditLimit, maxWithdrawalAmountForQuestionableAccount);
        banks.add(newBank);
        return newBank;
    }

    public void TransferFinances(Bank senderBank, Bank recipientBank) {}

    public void notifyInterest(){
        for (Bank bank : banks){
            bank.processInterest();
        }
    }

}
