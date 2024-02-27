package ru.itmo.entity.banks;

import ru.itmo.entity.accounts.CreditAccount;
import ru.itmo.entity.accounts.DebitAccountI;
import ru.itmo.entity.accounts.DepositAccountI;
import ru.itmo.model.client.IClient;

import java.util.Date;

public interface IAccountCreatable {
    CreditAccount createCreditAccount(IClient owner);
    DebitAccountI createDebitAccount(IClient owner);
    DepositAccountI createDepositAccount(IClient owner, Double startAmountMoney, Date dateEndTerm);
}
