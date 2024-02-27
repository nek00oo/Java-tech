package ru.itmo.entity.accounts;

import ru.itmo.entity.IInterestReceivable;
import ru.itmo.model.client.IClient;
import ru.itmo.model.Transaction;
import ru.itmo.type.OperationType;

/**
 * Debit account class, has a percentage on the balance
 * Inherited from Account {@link Account}
 * Implements the interface {@link IInterestReceivable}
 *
 * @author valer
 * @version 1.0
 * @since 2024-02-27
 */
public class DebitAccountI extends Account implements IInterestReceivable {

    public DebitAccountI(IClient owner, Long idAccount) {
        super(owner, idAccount);
    }

    @Override
    public boolean withdraw(Double amountMoney) {
        if (balance < amountMoney)
            return false;

        balance -= amountMoney;
        transactions.add(new Transaction(idTransactionCounter++, getIdAccount(), amountMoney, new OperationType.Withdraw()));
        return true;
    }

    @Override
    public void accrueInterest(Double ratioInterestRate) {
        balance += balance * ratioInterestRate;
    }
}
