package net.jaskar.bank;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    public final int routingNumber;
    private long lastTransactionId = 1;
    private Map<String, BankCustomer> customers = new HashMap<>();

    public Bank(int routingNumber) {
        this.routingNumber = routingNumber;
    }

    public BankCustomer getCustomer(String id) {
        return customers.get(id);
    }

    public void addCustomer(String name, double checkingInitialDeposit, double savingsInitialDeposit) {
        BankCustomer bankCustomer = new BankCustomer(name, checkingInitialDeposit, savingsInitialDeposit);
        customers.put(bankCustomer.getCustomerId(), bankCustomer);
    }

    public boolean doTransaction(String id, BankAccount.AccountType type, double amount) {
        BankCustomer customer = customers.get(id);
        if (customer == null) {
            System.out.println("Invalid customer id.");
            return false;
        }
        BankAccount customerAccount = customer.getAccount(type);
        if (customerAccount == null) {
            System.out.println("Invalid bank account.");
            return false;
        }
        if (customerAccount.getBalance() + amount < 0) {
            System.out.println("Insufficient funds.");
            return false;
        }
        customers.get(id).getAccount(type).commitTransaction(routingNumber, lastTransactionId++, id, amount);
        return true;
    }
}
