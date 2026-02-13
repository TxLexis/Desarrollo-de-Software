package edu.ucr.exceptions.c24249;

public class Ej5_CustomBusinessException {
    public static void main(String[] args) {
        Account acc = new Account("A-123", 50);

        try {
            withdraw(acc, 100);
            System.out.println("Withdrawal ok");
        } catch (InsufficientBalanceException e) {
            // TODO: user-facing message (actionable)
            System.out.println(
                    "Cannot withdraw " + e.getAttempted() + ". Available balance: " + e.getAvailable()
            );

        }
    }

    static void withdraw(Account acc, long amount) {
        // TODO:
        // - validate amount > 0 (IllegalArgumentException)
        if(amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
            }
        // - if amount > acc.balance -> throw InsufficientBalanceException(...)
        if(amount > acc.balance) {
            throw new InsufficientBalanceException (acc.id() , amount , acc.balance);
        }
        // - otherwise simulate balance update (your choice)
            System.out.println("Balance updated successfully");
    }

    record Account(String id, long balance) {}

    static class InsufficientBalanceException extends RuntimeException {
        // TODO: fields + constructor + accessors + useful message
        private final String accountId;
        private final long attempted;
        private final long available;

        public InsufficientBalanceException(String accountId, long attempted, long available) {
            super("Insufficient balance for account " + accountId + ": attempted " + attempted + ", available " + available);

            this.accountId = accountId;
            this.attempted = attempted;
            this.available = available;
        }

        public String getAccountId() {
            return accountId;
        }
        public long getAttempted() {
            return attempted;
        }
        public long getAvailable() {
            return available;
        }

    }
}