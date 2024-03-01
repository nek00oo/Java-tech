package ru.itmo.type;

public abstract class OperationType {
    private OperationType() {}
    public static class Withdraw extends OperationType {}
    public static class Transfer extends OperationType {
        private final Long idRecipient;

        public Transfer(Long idRecipient) {
            this.idRecipient = idRecipient;
        }

        public Long getIdRecipient() {
            return idRecipient;
        }
    }
    public static class Deposit extends OperationType {}
}