package net.jaskar.bank;

public class BankAccount {
    public enum AccountType {CHECKING, SAVINGS}
    private final AccountType type;
    private final double balance;

    BankAccount(AccountType type, double balance) {
        this.type = type;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "%s $%.2f".formatted(type, balance);
    }
}
