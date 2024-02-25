package ru.itmo.type;

public abstract class AccountType{
    private AccountType() {}
    public static class Debit extends AccountType {}
    public static class Deposit extends AccountType {}
    public static class Credit extends AccountType {}
}
