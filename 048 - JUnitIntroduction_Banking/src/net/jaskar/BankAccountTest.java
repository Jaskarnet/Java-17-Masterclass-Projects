package net.jaskar;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    private BankAccount account;
    private static int count;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("This executes before all test cases. Count = " + count++);
    }

    @BeforeEach
    public void setup() {
        account = new BankAccount("Tim", "Buchalka", 1000.00, BankAccount.CHECKING);
        System.out.println("Running a test...");
    }

    @Test
    public void deposit() {
        double balance = account.deposit(200.00, true);
        assertEquals(1200.00, balance, 0);
        assertEquals(1200, account.getBalance(), 0);
    }

    @Test
    public void withdraw_branch() {
        double balance = account.withdraw(600.00, true);
        assertEquals(400.00, balance,0);
    }

    @Test
    public void withdraw_notBranch() {
        // Expect an IllegalArgumentException to be thrown
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(600.00, false);  // Code that should throw the exception
        });
    }

    @Test
    public void getBalance_deposit() {
        account.deposit(200.00, true);
        assertEquals(1200, account.getBalance(), 0);
    }

    @Test
    void getBalance_withdraw() {
        account.withdraw(200.00, true);
        assertEquals(800, account.getBalance(), 0);
    }

    @Test
    public void isChecking_true() {
        assertTrue(account.isChecking(), "The account is not a checking account");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("This executes after all test cases. Count = " + count++);
    }

    @AfterEach
    public void teardown() {
        System.out.println("Count = " + count++);
    }
}