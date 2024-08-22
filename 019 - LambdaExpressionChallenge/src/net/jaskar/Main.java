package net.jaskar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    private static Random random = new Random();

    private static char getRandomChar(char startChar, char endChar) {
        return (char) random.nextInt((int) startChar, (int) endChar + 1);
    }

    public static void main(String[] args) {
        String[] names = {"Anna", "Mike", "Bob", "Jesse", "Walter", "Skyler"};
        System.out.println(Arrays.toString(names));

        // Transform names to all uppercase
        // `Arrays.setAll` modifies each element of the array using the provided lambda function.
        // The lambda function converts each name to uppercase.
        System.out.println("--> Transform to uppercase");
        Arrays.setAll(names, i -> names[i].toUpperCase());
        System.out.println(Arrays.toString(names));

        List<String> backedByArray = Arrays.asList(names);

        // Add a randomly generated middle initial, and include period
        // `List.replaceAll` replaces each element of the list using the provided lambda function.
        // The lambda function appends a randomly generated middle initial and a period to each name.
        System.out.println("--> Add random middle initial");
        backedByArray.replaceAll(name -> name + " " + getRandomChar('A', 'Z') + ".");
        System.out.println(Arrays.toString(names));

        // Add a last name that is the reverse of the first name
        // `List.replaceAll` replaces each element of the list using the provided lambda function.
        // The lambda function adds a reversed version of the first name as the last name.
        System.out.println("--> Add reverse name as last name");
        backedByArray.replaceAll(name -> name + " " + new StringBuilder(name.split(" ")[0]).reverse());
        Arrays.asList(names).forEach(s -> System.out.println(s)); // Same as: backedByArray.forEach();

        // Create a new modifiable ArrayList from names array, removing any names where the last name equals the first name
        // `new ArrayList<>(List.of(names))` creates a new ArrayList from the names array.
        // `removeIf` removes elements where the last name is the same as the first name, based on the lambda condition.
        System.out.println("--> Remove names where first = last");
        List<String> newList = new ArrayList<>(List.of(names)); //Same as: new ArrayList<>(backedByArray);
        newList.removeIf(name -> {
            String firstName = name.substring(0, name.indexOf(" "));
            String surname = name.substring(name.lastIndexOf(" ") + 1);
            return firstName.equals(surname);
        });
        newList.forEach(s -> System.out.println(s));
    }
}
