package ru.itmo.entity.banks;

import ru.itmo.entity.IPublisher;

public interface IBank extends IFinancialOperationManager, IAccountCreatable, IPublisher {
}
