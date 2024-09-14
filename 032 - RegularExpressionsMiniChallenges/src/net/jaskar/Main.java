package net.jaskar;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Challenge 1: Check if the input string matches the exact string "Hello, World!"
        String challenge = "Hello, World!";
        String input = "Hello, World!";
        String input1 = "Hello, Worlds!";
        System.out.println(input.matches(challenge));
        System.out.println(input1.matches(challenge));

        // Challenge 2: Match a string that starts with an uppercase letter,
        // followed by lowercase letters or spaces, and ends with a period.
        String challenge2 = "[A-Z][a-z\\s]+[.]";

        for (String s : List.of("The bike is red.",
                "I am a new student.",
                "hello world.",
                "How are you?")) {
            boolean matched = s.matches(challenge2);
            System.out.println(matched + ": " + s);
        }

        // Challenge 3: Match a string that starts with an uppercase letter,
        // can include any character, and ends with a punctuation mark (period, exclamation mark, or question mark).
        // ^[A-Z]      : Start with an uppercase letter
        // [\\p{all}]+ : One or more occurrences of any character (all Unicode characters)
        // [.?!]$      : Ends with one of '.', '?', or '!'
        String challenge3 = "^[A-Z][\\p{all}]+[.?!]$";

        for (String s : List.of("The bike is red, and has flat tires.",
                "I love being a new L.P.A. student!",
                "Hello, friends and family: Welcome!",
                "How are you, Mary?")) {
            boolean matched = s.matches(challenge3);
            System.out.println(matched + ": " + s);
        }
    }
}
