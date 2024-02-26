package ru.itmo.type;

public abstract class OperationType {
    private OperationType() {}
    public static class Withdraw extends OperationType {}
    public static class Transfer extends OperationType {}
    public static class Replenishment extends OperationType {}
    public static class Deposit extends OperationType {}
}