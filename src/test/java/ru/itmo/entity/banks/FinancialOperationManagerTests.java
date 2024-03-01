package ru.itmo.entity.banks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.entity.accounts.CreditAccount;
import ru.itmo.entity.accounts.DebitAccount;
import ru.itmo.entity.banks.validate.IValidatorBank;
import ru.itmo.entity.banks.validate.ValidatorBank;
import ru.itmo.model.client.ClientBuilder;
import ru.itmo.model.client.IClient;
import ru.itmo.model.client.validate.IValidatorClient;
import ru.itmo.model.client.validate.ValidatorClient;

public class FinancialOperationManagerTests {


    @Test
    void transfer(){
        IValidatorBank validatorBank = new ValidatorBank();
        IBankBuilder bankBuilder = new BankBuilder(validatorBank);
        CentralBank centralBank = new CentralBank(bankBuilder);
        IBank bank = centralBank.CreateBank(0.15, 50000.0, 2000.0, 0.001);
        IValidatorClient validatorClient = new ValidatorClient();
        IClient client1 = new ClientBuilder(validatorClient)
                .addFirstname("Nikita")
                .addLastname("Morozov").build();
        DebitAccount debitAccount = bank.createDebitAccount(client1);
        debitAccount.deposit(500.0);
        IClient client2 = new ClientBuilder(validatorClient)
                .addFirstname("Vasya")
                .addLastname("Lukov").build();
        CreditAccount creditAccount = bank.createCreditAccount(client2);
        creditAccount.deposit(100.0);
        bank.transfer(0L, 1L, 300.0);
        Assertions.assertEquals(400.0, creditAccount.getBalance());
    }
}
