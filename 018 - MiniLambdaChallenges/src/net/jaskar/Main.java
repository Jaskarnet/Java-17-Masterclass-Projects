package net.jaskar;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        String mySentence = "I have a very long sentence with many words.";

        // Lambda: split sentence and print each word using a for loop
        System.out.println("--------------0");
        Consumer<String> printWordsLambda = sentence -> {
            String[] parts = sentence.split(" ");
            for (String part : parts)
                System.out.println(part);
        };
        printWordsLambda.accept(mySentence);

        // Lambda: split sentence and print each word using forEach
        System.out.println("--------------1");
        Consumer<String> printWordsForEach = sentence -> {
            String[] parts = sentence.split(" ");
            Arrays.asList(parts).forEach(s -> System.out.println(s));
        };
        printWordsForEach.accept(mySentence);

        // Lambda: concise version, combining split and forEach
        System.out.println("--------------2");
        Consumer<String> printWordsConcise = sentence ->
                Arrays.asList(sentence.split(" ")).forEach(s -> System.out.println(s));
        printWordsConcise.accept(mySentence);

        // Lambda: extract every second character
        System.out.println("--------------3");
        UnaryOperator<String> everySecondChar = source -> {
            StringBuilder returnVal = new StringBuilder();
            for (int i = 0; i < source.length(); i++)
                if (i % 2 == 1)
                    returnVal.append(source.charAt(i));
            return returnVal.toString();
        };
        System.out.println(everySecondChar.apply("1234567890"));

        // Apply the UnaryOperator through a method
        System.out.println("--------------4");
        String myString = "1234567890";
        System.out.println(everySecondCharacter(everySecondChar, myString));

        // Two Supplier examples
        System.out.println("--------------5");
        Supplier<String> iLoveJava = () -> "I love Java!";
        Supplier<String> iLoveJava2 = () -> {return "I love Java!";};
        String supplierResult = iLoveJava.get();
        String supplierResult2 = iLoveJava2.get();
        System.out.println(supplierResult);
        System.out.println(supplierResult2);
    }

    // Method to apply any Function to a string
    public static String everySecondCharacter(Function<String, String> function, String string) {
        return function.apply(string);
    }
}
