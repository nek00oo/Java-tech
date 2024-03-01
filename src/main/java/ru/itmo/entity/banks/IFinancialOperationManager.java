package ru.itmo.entity.banks;

/**
 * Interface for managing financial operations on accounts.
 */
public interface IFinancialOperationManager {

    /**
     * Process accrued interest for eligible accounts.
     */
    void processInterest();

    /**
     * Change the interest rate for applicable accounts.
     *
     * @param newInterestRate The new interest rate.
     */
    void changeRatioInterestRate(Double newInterestRate);

    /**
     * Change the credit limit for credit accounts.
     *
     * @param newCreditLimit The new credit limit.
     */
    void changeCreditLimit(Double newCreditLimit);

    /**
     * Withdraw money from a specified account.
     *
     * @param idAccount    The ID of the account.
     * @param amountMoney  The amount of money to withdraw.
     * @return True if the withdrawal was successful, false otherwise.
     */
    boolean withdraw(Long idAccount, Double amountMoney);

    /**
     * Deposit money into a specified account.
     *
     * @param idAccount    The ID of the account.
     * @param amountMoney  The amount of money to deposit.
     * @return True if the deposit was successful, false otherwise.
     */
    boolean deposit(Long idAccount, Double amountMoney);

    boolean transfer(Long idSenderAccount, Long idRecipientAccount, Double amountMoney);

    /**
     * Cancel a transaction for a specified account.
     *
     * @param idAccount      The ID of the account.
     * @param idTransaction  The ID of the transaction to cancel.
     * @return True if the transaction cancellation was successful, false otherwise.
     */
    boolean cancellationTransaction(Long idAccount, Long idTransaction);
}

