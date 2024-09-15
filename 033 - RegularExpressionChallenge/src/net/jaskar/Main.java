package net.jaskar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        // Sample block of email addresses to be validated
        String emailAddresses = """
                john.boy@valid.com
                john.boy@invalid
                jane.doe-smith@valid.co.uk
                jane_Doe1976@valid.co.uk
                bob-1964@valid.net
                bob!@invalid.com
                elaine@valid-test.com.au
                elaineinvalid1983@.com
                david@valid.io
                david@invalid..com""";

        // The regular expression pattern to match valid email addresses
        // Explanation of regex:
        // 1. ([\\w.-]+): Captures the username part of the email address, which may contain:
        //    - Word characters (letters, digits, and underscores) [\\w]
        //    - Dots (.) and hyphens (-)
        //    - The "+" means one or more of these characters
        // 2. @: Matches the literal "@" symbol in the email
        // 3. (([\\w-]+\\.)+[\\w-]{2,}): Captures the domain part of the email address
        //    - ([\\w-]+\\.)+ : Matches the domain's subparts (e.g., "valid.", "co.", "test.", etc.)
        //      which consist of word characters and hyphens, followed by a dot
        //    - [\\w-]{2,} : Matches the top-level domain (e.g., "com", "uk", "net"),
        //      which should be at least 2 characters long
        Pattern pattern = Pattern.compile("([\\w.-]+)@(([\\w-]+\\.)+[\\w-]{2,})");

        System.out.println("First way of printing: ");
        pattern.matcher(emailAddresses).results()
                .forEach(mr -> {
                    System.out.println(mr.group());  // Print the full matched email
                    System.out.println("\tUsername: " + mr.group(1));  // Extract and print the username part
                    System.out.println("\tDomain name: " + mr.group(2));  // Extract and print the domain part
                });

        System.out.println("\nSecond way of printing: ");
        // Split the sample email addresses into individual lines and process each one
        String[] emailSamples = emailAddresses.lines().toArray(String[]::new);
        for (String email : emailSamples) {
            Matcher eMatcher = pattern.matcher(email);
            boolean matched = eMatcher.matches();  // Check if the email matches the regex pattern
            System.out.print(email + " is " + (matched ? "VALID " : "INVALID "));
            if (matched) {
                // If the email is valid, print the extracted username and domain
                System.out.printf("[username=%s, domain=%s]%n",
                        eMatcher.group(1),
                        eMatcher.group(2));
            } else {
                System.out.println();  // Print a new line for invalid emails
            }
        }
    }
}
