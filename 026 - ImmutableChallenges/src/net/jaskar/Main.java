package net.jaskar;

import net.jaskar.bank.Bank;
import net.jaskar.bank.BankAccount;
import net.jaskar.bank.BankCustomer;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank(3214567);
        bank.addCustomer("Joe", 500.00,
                10000.00);

        BankCustomer joe = bank.getCustomer("000000010000000");
        System.out.println(joe);

        if (bank.doTransaction(joe.getCustomerId(), BankAccount.AccountType.CHECKING,
                35)) {
            System.out.println(joe);
        }

        if (bank.doTransaction(joe.getCustomerId(), BankAccount.AccountType.CHECKING,
                -535)) {
            System.out.println(joe);
        }

        BankAccount checking = joe.getAccount(BankAccount.AccountType.CHECKING);
        var transactions = checking.getTransactions();
        transactions.forEach((k, v) -> System.out.println(k + ": " + v));

        joe.getAccount(BankAccount.AccountType.CHECKING).getTransactions().clear();
        System.out.println("------------------");
        joe.getAccount(BankAccount.AccountType.CHECKING).getTransactions()
                .forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
