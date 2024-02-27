package ru.itmo.entity;

/**
 * interface for accounts that can receive accrued interest
 */
public interface IInterestReceivable {
    void accrueInterest(Double ratioInterestRate);
}
