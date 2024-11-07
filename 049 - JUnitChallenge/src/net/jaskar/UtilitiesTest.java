package net.jaskar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {
    private static Utilities utilities;

    @BeforeAll
    static void beforeAll() {
        utilities = new Utilities();
    }

    @Test
    void everyNthChar_SecondCharacter_ReturnsEverySecondChar() {
        char[] result = utilities.everyNthChar("hello".toCharArray(), 2);
        assertArrayEquals("el".toCharArray(), result);
    }

    @Test
    void everyNthChar_NValueGreaterThanWordsLength_ReturnsSourceArray() {
        char[] source = new char[] {'h', 'e', 'l', 'l', 'o'};
        char[] result = utilities.everyNthChar(source, source.length + 1);
        assertArrayEquals(source, result);
    }

    @ParameterizedTest
    @CsvSource({
            "ABCDEFF, ABCDEF",
            "AB88EFFG, AB8EFG",
            "112233445566, 123456",
            "ZYZQQB, ZYZQB",
            "A, A"
    })
    void removePairs_WithPairs_RemovesCorrectly(String input, String expectedOutput) {
        String result = utilities.removePairs(input);
        assertEquals(expectedOutput, result, "Failed to remove pairs from input: '" + input + "'");
    }

    @Test
    void removePairs_SingleCharacter_ReturnsSame() {
        String result = utilities.removePairs("A");
        assertEquals("A", result, "Failed to handle single character string 'A'");
    }

    @Test
    void removePairs_NullInput_ReturnsNull() {
        String result = utilities.removePairs(null);
        assertNull(result, "Did not get null returned when argument passed was null");
    }

    @Test
    void converter_ValidConversion_ReturnsCorrectResult() {
        int result = utilities.converter(10, 5);
        assertEquals(300, result, "Converter did not return the expected result");
    }

    @Test
    void converter_InvalidConversion_ThrowsArithmeticException() {
        assertThrowsExactly(ArithmeticException.class, () -> {
            utilities.converter(10, 0);
        }, "Converter did not throw ArithmeticException when dividing by zero");
    }


    @Test
    void nullIfOddLength_OddLength_ReturnsNull() {
        String result = utilities.nullIfOddLength("hello");
        assertNull(result, "Did not get null returned when length of the argument was odd");
    }

    @Test
    void nullIfOddLength_EvenLength_ReturnsSourceString() {
        String source = "heyo";
        String result = utilities.nullIfOddLength(source);
        assertEquals(source, result,
                "Did not get source string returned when length of the argument was even");
    }
}