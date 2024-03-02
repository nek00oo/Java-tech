package ru.itmo.entity.accounts;

import lombok.Getter;
import ru.itmo.entity.IInterestReceivable;
import ru.itmo.model.client.IClient;
import ru.itmo.model.Transaction;
import ru.itmo.type.OperationType;

import java.util.Date;

/**
 * The class of the deposit account has a percentage on the initial amount
 * Inherited from Account {@link Account}
 * Implements the interface {@link IInterestReceivable}
 */
@Getter
public class DepositAccount extends Account implements IInterestReceivable {
    private final Double startAmount;
    private Double accumulatedInterest;
    private Date dateEndTerm;

    public DepositAccount(IClient owner, Double startAmount, Long idAccount, Date dateEndTerm) {
        super(owner, idAccount);
        if (startAmount < 0)
            throw new IllegalArgumentException("The start amount must be positive");

        this.accumulatedInterest = 0.0;
        this.dateEndTerm = dateEndTerm;
        this.startAmount = startAmount;
        this.balance = 0.0;
    }

    /**
     * the method for the operation of withdrawing money from the account,
     * it is allowed to withdraw after the expiration of the account
     *
     * @param amountMoney the amount of money to withdraw
     * @return True if the withdrawal was successful, false otherwise.
     */
    @Override
    public boolean withdraw(Double amountMoney) {
        if (balance < amountMoney)
            return false;
        balance -= amountMoney;
        transactions.add(new Transaction(idTransactionCounter++, getIdAccount(), amountMoney, new OperationType.Withdraw()));
        return true;
    }

    /**
     * The method accumulates funds in accumulatedInterest
     *
     * @param ratioInterestRate the interest rate ratio
     */
    @Override
    public void accrueInterest(Double ratioInterestRate) {
        accumulatedInterest += startAmount * ratioInterestRate;
    }

    /**
     * The method writes off the accumulated funds and credits them to the balance
     */
    public void getAccumulatedInterest() {
        balance += accumulatedInterest;
    }
}