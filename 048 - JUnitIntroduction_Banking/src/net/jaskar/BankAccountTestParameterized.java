package net.jaskar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountTestParameterized {
    private BankAccount account;

    @BeforeEach
    public void setup() {
        account = new BankAccount("Tim", "Buchalka", 1000.00, BankAccount.CHECKING);
        System.out.println("Running a test...");
    }

    // Parameterized Test method with CsvSource
    @ParameterizedTest
    @CsvSource({
            "100.00, true, 1100.00",
            "200.00, true, 1200.00",
            "325.14, true, 1325.14",
            "489.33, true, 1489.33",
            "1000.00, true, 2000.00"
    })
    void getBalance_deposit(double amount, boolean branch, double expected) {
        account.deposit(amount, branch);
        assertEquals(expected, account.getBalance(), 0.01);
    }

    // Alternative method to parameterize the test using MethodSource to supply arguments from a static method:

//    @ParameterizedTest
//    @MethodSource("testConditions")
//    void deposit(double amount, boolean branch, double expected) {
//        account.deposit(amount, branch);
//        assertEquals(expected, account.getBalance(), 0.01);
//    }
//
//    static Stream<Arguments> testConditions() {
//        return Stream.of(
//                Arguments.of(100.00, true, 1100.00),
//                Arguments.of(200.00, true, 1200.00),
//                Arguments.of(325.14, true, 1325.14),
//                Arguments.of(489.33, true, 1489.33),
//                Arguments.of(1000.00, true, 2000.00)
//        );
//    }
}
