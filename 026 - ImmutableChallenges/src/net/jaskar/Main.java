package net.jaskar;

import net.jaskar.bank.BankAccount;
import net.jaskar.bank.BankCustomer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        BankCustomer joe = new BankCustomer("Joe", 500.00, 10000.00);
        System.out.println(joe);

        List<BankAccount> accounts = joe.getBankAccounts();
        accounts.clear();
        System.out.println(joe);
    }
}
