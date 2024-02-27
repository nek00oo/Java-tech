package ru.itmo.entity.accounts;

import ru.itmo.model.client.IClient;
import ru.itmo.model.Transaction;
import ru.itmo.type.OperationType;

/**
 * Credit account class, has a credit limit, has no interest on the balance
 * Inherited from Account {@link Account}
 *
 * @author valer
 * @version 1.0
 * @since 2024-02-27
 */
public class CreditAccount extends Account {
    private Double creditLimit;
    private final Double commission;

    public CreditAccount(IClient owner, Double creditLimit, Double commission, Long idAccount) {
        super(owner, idAccount);
        this.creditLimit = creditLimit;
        this.commission = commission;
    }

    /**
     * the method for the withdrawal operation, if there is money on the balance,
     * then the money is withdrawn from it, and then from the limit
     *
     * @param amountMoney the amount of money to withdraw
     * @return True if the withdrawal was successful, false otherwise.
     */
    @Override
    public boolean withdraw(Double amountMoney) {
        if (amountMoney > balance + creditLimit)
            return false;

        if (amountMoney <= balance) {
            balance -= amountMoney;
        } else if (amountMoney <= balance + creditLimit) {
            double remain = amountMoney - balance;
            balance = 0.0;
            creditLimit -= remain + remain * commission;
        }
        transactions.add(new Transaction(idTransactionCounter++, getIdAccount(), amountMoney, new OperationType.Withdraw()));
        return true;
    }
}
