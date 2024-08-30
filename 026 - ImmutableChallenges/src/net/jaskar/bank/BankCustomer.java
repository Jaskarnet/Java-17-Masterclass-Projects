package net.jaskar.bank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankCustomer {
    private final String name;
    private final int id;
    private final List<BankAccount> bankAccounts = new ArrayList<>();

    private static int LAST_ID = 1;

    public BankCustomer(String name, double checkingAmount, double savingsAmount) {
        this.name = name;
        this.id = LAST_ID++;
        bankAccounts.add(new BankAccount(BankAccount.AccountType.CHECKING, checkingAmount));
        bankAccounts.add(new BankAccount(BankAccount.AccountType.SAVINGS, savingsAmount));
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<BankAccount> getBankAccounts() {
        return new ArrayList<>(bankAccounts);
    }

    @Override
    public String toString() {
        String[] accountStrings = new String[bankAccounts.size()];
        Arrays.setAll(accountStrings, i -> bankAccounts.get(i).toString());
        return "Customer: %s (id:%015d)%n\t%s%n".formatted(name, id, String.join("\n\t", accountStrings));
    }
}