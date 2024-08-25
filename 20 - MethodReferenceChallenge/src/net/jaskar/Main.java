package net.jaskar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        String[] names = {"Anna", "Mike", "Bob", "Jesse", "Walter", "Skyler"};
        List<UnaryOperator<String>> operations = new ArrayList<>(List.of(
                // Make each name upper case
                String::toUpperCase,
                // Add a random middle initial
                name -> name + " " + getRandomChar('A', 'Z') + ".",
                // Add a last name which should be the reverse of first
                name -> name + " " + getReversedName(name.split(" ")[0])
        ));
        applyEachFunctionToName(names, operations);
    }

    public static void applyEachFunctionToName(String[] names, List<UnaryOperator<String>> operations) {
        //operations.forEach(operation -> Arrays.asList(names).replaceAll(operation));

        List<String> backedByArray = Arrays.asList(names);
        for (var operation : operations) {
            backedByArray.replaceAll(operation);
            System.out.println(Arrays.toString(names));
        }
    }

    private static char getRandomChar(char startChar, char endChar) {
        return (char) random.nextInt((int) startChar, (int) endChar + 1);
    }

    private static String getReversedName(String firstName) {
        return new StringBuilder(firstName).reverse().toString();
    }
}
