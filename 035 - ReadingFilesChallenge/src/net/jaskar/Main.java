package net.jaskar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Path path = Path.of("article.txt");
        String input = null;
        try {
            input = Files.readString(path);
            input = input.replaceAll("\\p{P}", "");

            Pattern pattern = Pattern.compile("\\w{5,}");
            Matcher matcher = pattern.matcher(input);
            Map<String, Long> results = new HashMap<>();
            while (matcher.find()) {
                String word = matcher.group().toLowerCase();
                results.merge(word, 1L, (o, n) -> o += n);
            }

            var sortedEntries = new ArrayList<>(results.entrySet());
            sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            for (int i = 0; i < Math.min(10, sortedEntries.size()); i++) {
                var entry = sortedEntries.get(i);
                System.out.println(entry.getKey() + " - " + entry.getValue() + " times");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-".repeat(30));

        try (Scanner scanner = new Scanner(path)) {
            scanner.tokens()
                    .map(word -> word.replaceAll("\\p{P}", "").toLowerCase())
                    .filter(word -> word.length() >= 5)
                    .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(10)
                    .forEach(e -> System.out.println(e.getKey() + " - " + e.getValue() + " times"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
