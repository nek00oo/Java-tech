package ru.itmo;

import ru.itmo.entity.banks.BankBuilder;
import ru.itmo.entity.banks.CentralBank;
import ru.itmo.entity.accounts.DebitAccount;
import ru.itmo.entity.banks.IBank;
import ru.itmo.entity.banks.IBankBuilder;
import ru.itmo.entity.banks.validate.IValidatorBank;
import ru.itmo.entity.banks.validate.ValidatorBank;
import ru.itmo.model.client.ClientBuilder;
import ru.itmo.model.client.IClient;
import ru.itmo.model.client.validate.IValidatorClient;
import ru.itmo.model.client.validate.ValidatorClient;

public class Main {
    public static void main(String[] args) {
        IValidatorBank validatorBank = new ValidatorBank();
        IBankBuilder bankBuilder = new BankBuilder(validatorBank);
        CentralBank centralBank = new CentralBank(bankBuilder);
        IBank sberbank = centralBank.CreateBank(0.15, 50000.0, 2000.0, 0.001);
        IValidatorClient validatorClient = new ValidatorClient();
        IClient client = new ClientBuilder(validatorClient)
                .addFirstname("Nikita9")
                .addLastname("Podyshkin").build();
        DebitAccount debitAccount = sberbank.createDebitAccount(client);
        debitAccount.deposit(500.0);
    }
}