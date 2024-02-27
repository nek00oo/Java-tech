package ru.itmo.model;

import ru.itmo.type.OperationType;

/**
 * Transaction Model
 *
 * @param idTransaction The ID transaction
 * @param idAccount     The ID of the account in which the transaction was carried out
 * @param amountMoney   The amount of money in the operation
 * @param operationType Type of operation
 */
public record Transaction(long idTransaction, long idAccount, double amountMoney, OperationType operationType) {
}
