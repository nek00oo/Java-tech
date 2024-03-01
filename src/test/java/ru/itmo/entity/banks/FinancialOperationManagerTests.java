package ru.itmo.entity.banks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.itmo.entity.accounts.CreditAccount;
import ru.itmo.entity.accounts.DebitAccount;
import ru.itmo.model.client.ClientBuilder;
import ru.itmo.model.client.IClient;

public class FinancialOperationManagerTests {
    private IBank bank;
    private DebitAccount debitAccount;
    private CreditAccount creditAccount;

    @BeforeEach
    void setUp(){
        IBankBuilder bankBuilder = new BankBuilder();
        CentralBank centralBank = new CentralBank(bankBuilder);
        bank = centralBank.CreateBank(0.15, 50000.0, 2000.0, 0.001);
        IClient client1 = new ClientBuilder()
                .addFirstname("Nikita")
                .addLastname("Morozov").build();
        IClient client2 = new ClientBuilder()
                .addFirstname("Vasya")
                .addLastname("Lukov")
                .addAddress("Prospect Kutuzov")
                .addPassportNumber("3255232").build();
        debitAccount = bank.createDebitAccount(client1);
        creditAccount = bank.createCreditAccount(client2);

    }

    @Test
    void deposit_returnTrue(){

        // Act
        debitAccount.deposit(200.0);

        // Assert
        Assertions.assertEquals(200.0, debitAccount.getBalance());
    }


    @Test
    void transfer_withEnoughMoney_returnTrue(){

        // Arrange
        debitAccount.deposit(500.0);
        creditAccount.deposit(100.0);

        // Act
        Assertions.assertTrue(bank.transfer(0L, 1L, 300.0));

        // Assert
        Assertions.assertEquals(200.0, debitAccount.getBalance());
        Assertions.assertEquals(400.0, creditAccount.getBalance());
    }

    @Test
    void transfer_withNotEnoughMoney_returnFalse(){

        // Arrange
        debitAccount.deposit(200.0);
        creditAccount.deposit(100.0);

        // Act
        Assertions.assertFalse(bank.transfer(0L, 1L, 300.0));

        // Assert
        Assertions.assertEquals(200.0, debitAccount.getBalance());
        Assertions.assertEquals(100.0, creditAccount.getBalance());
    }

    @Test
    void withdraw_withEnoughMoney_returnTrue(){

        // Arrange
        debitAccount.deposit(500.0);

        // Act
        Assertions.assertTrue(bank.withdraw(0L, 200.0));

        // Assert
        Assertions.assertEquals(300.0, debitAccount.getBalance());

    }

    @Test
    void withdraw_withNotEnoughMoney_returnFalse(){

        // Arrange
        debitAccount.deposit(100.0);

        // Act
        Assertions.assertFalse(bank.withdraw(0L, 200.0));

        // Assert
        Assertions.assertEquals(100.0, debitAccount.getBalance());
    }

    @Test
    void withdraw_withNotEnoughBalanceOnCreditAccount_returnFalse(){

        // Act
        Assertions.assertTrue(bank.withdraw(1L, 500.0));

        // Assert
        Assertions.assertEquals(50_000 - (500.0 + 500.0 * creditAccount.getCommission()),
                creditAccount.getCreditLimit());
    }

    @Test
    void withdraw_whenQuestionableAccount_returnFalse(){

        // Arrange
        debitAccount.deposit(2200.0);

        // Act
        Assertions.assertFalse(bank.withdraw(0L, 2100.0));

        // Assert
        Assertions.assertEquals(2200.0, debitAccount.getBalance());
    }

    @Test
    void withdraw_whenConfirmAccount_returnTrue(){

        // Arrange
        creditAccount.deposit(2200.0);

        // Act
        Assertions.assertTrue(bank.withdraw(1L, 2100.0));

        // Assert
        Assertions.assertEquals(100.0, creditAccount.getBalance());
    }

    @Test
    void cancellationTransaction_returnTrue(){

        // Arrange
        debitAccount.deposit(200.0);
        creditAccount.deposit(100.0);

        // Act
        bank.transfer(0L, 1L, 200.0);
        bank.cancellationTransaction(0L, 2L);

        //Assert
        Assertions.assertEquals(200.0, debitAccount.getBalance());
        Assertions.assertEquals(100.0, creditAccount.getBalance());
    }
}
