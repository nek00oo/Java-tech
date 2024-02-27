package ru.itmo.entity.banks;

import ru.itmo.entity.accounts.CreditAccount;
import ru.itmo.entity.accounts.DebitAccount;
import ru.itmo.entity.accounts.DepositAccount;
import ru.itmo.model.client.IClient;

import java.util.Date;

public interface IAccountCreatable {
    CreditAccount createCreditAccount(IClient owner);
    DebitAccount createDebitAccount(IClient owner);
    DepositAccount createDepositAccount(IClient owner, Double startAmountMoney, Date dateEndTerm);
}
