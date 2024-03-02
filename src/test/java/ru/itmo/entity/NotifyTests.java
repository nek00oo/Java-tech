package ru.itmo.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.itmo.entity.accounts.CreditAccount;
import ru.itmo.entity.accounts.DebitAccount;
import ru.itmo.entity.accounts.DepositAccount;
import ru.itmo.entity.banks.BankBuilder;
import ru.itmo.entity.banks.CentralBank;
import ru.itmo.entity.banks.IBank;
import ru.itmo.entity.banks.IBankBuilder;
import ru.itmo.model.client.ClientBuilder;
import ru.itmo.model.client.IClient;

import java.util.Date;

public class NotifyTests {

    private IBank bank;
    private DebitAccount debitAccount;
    private CreditAccount creditAccount;
    private DepositAccount depositAccount;
    private CentralBank centralBank;

    @BeforeEach
    void setUp() {
        IBankBuilder bankBuilder = new BankBuilder();
        centralBank = new CentralBank(bankBuilder);
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
        Date endTermDate = new Date(1342);
        depositAccount = bank.createDepositAccount(client2, 200.0, endTermDate);

    }

    @Test
    void notifyInterest_whenIInterestReceivableAccount_accrualInterest() {

        // Arrange
        debitAccount.deposit(500.0);
        depositAccount.deposit(500.0);

        // Act
        centralBank.notifyInterest();
        depositAccount.getAccumulatedInterest();

        // Assert
        Assertions.assertEquals(500.0 + 500.0 * 0.15, debitAccount.getBalance());
        Assertions.assertEquals(500 + 200.0 * 0.15, depositAccount.getBalance());

    }

    @Test
    void notifyInterest_whenCreditAccount_notAccrualInterest() {

        // Arrange
        creditAccount.deposit(500.0);

        // Act
        centralBank.notifyInterest();

        // Assert
        Assertions.assertEquals(500.0, creditAccount.getBalance());

    }

    @Test
    void changeRatioInterestRate(){

        // Arrange
        bank.addObserver(depositAccount);

        // Act
        bank.changeRatioInterestRate(0.13);

        //Assert
        String message = depositAccount.getMessages().get(0);
        Assertions.assertEquals("received notification: Changing the percentage to the balance :0.15 -> 0.13", message);


    }

}
