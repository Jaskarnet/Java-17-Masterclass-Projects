package net.jaskar.bank;

import net.jaskar.dto.Transaction;

import java.util.*;

public class BankAccount {
    private final AccountType accountType;
    private double balance;
    private Map<Long, Transaction> transactions = new LinkedHashMap<>();

    public enum AccountType {CHECKING, SAVINGS}

    BankAccount(AccountType accountType, double balance) {
        this.accountType = accountType;
        this.balance = balance;
    }

    void commitTransaction(int routingNumber, long transactionId, String customerId, double amount) {
        balance += amount;
        transactions.put(transactionId, new Transaction(routingNumber, Integer.parseInt(customerId), transactionId, amount));
    }

    public Map<Long, String> getTransactions() {
        Map<Long, String> txMap = new LinkedHashMap<>();
        for (var tx : transactions.entrySet()) {
            txMap.put(tx.getKey(), tx.getValue().toString());
        }
        return txMap;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "%s $%.2f".formatted(accountType, balance);
    }
}
