package ru.itmo;

import ru.itmo.entity.banks.Bank;
import ru.itmo.entity.banks.CentralBank;
import ru.itmo.entity.accounts.DebitAccount;
import ru.itmo.model.client.ClientBuilder;
import ru.itmo.model.client.IClient;

public class Main {
    public static void main(String[] args) {
        CentralBank centralBank = new CentralBank();
        Bank sberbank = centralBank.CreateBank(0.15, 50000.0, 2000.0, 0.001);
        IClient client = new ClientBuilder()
                .addFirstname("Nikita")
                .addLastname("Podyshkin").build();
        DebitAccount debitAccount = sberbank.createDebitAccount(client);
        debitAccount.deposit(500.0);
    }
}