package ru.itmo.entity;

import ru.itmo.type.OperationType;

public record Transaction(long idTransaction, long idAccount, double amountMoney, OperationType operationType){}
