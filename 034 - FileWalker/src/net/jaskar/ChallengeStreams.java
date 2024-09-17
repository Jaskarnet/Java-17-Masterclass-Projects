package net.jaskar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ChallengeStreams {
    public static void main(String[] args) {
        // Define the starting directory for the file traversal. This will be the parent directory.
        Path startingPath = Path.of("..");

        // Get the index of the starting directory to use for grouping files by their immediate parent directory.
        int index = startingPath.getNameCount();

        try (var paths = Files.walk(startingPath, Integer.MAX_VALUE)) {
            // Walk the file tree starting from 'startingPath' and process each file.
            paths
                    // Filter out only regular files (ignore directories).
                    .filter(Files::isRegularFile)
                    // Group the files by their immediate parent directory path.
                    .collect(Collectors.groupingBy(
                            // Extract the subpath that represents the immediate parent directory.
                            p -> p.subpath(index, index + 1),
                            // Summarize the file sizes for each group.
                            Collectors.summarizingLong(
                                    p -> {
                                        try {
                                            // Return the size of the file. Handle IO exceptions by wrapping them in RuntimeException.
                                            return Files.size(p);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                            )
                    ))
                    // Convert the grouped results into a stream for further processing.
                    .entrySet()
                    .stream()
                    // Sort the entries by their directory path in ascending order.
                    .sorted(Comparator.comparing(e -> e.getKey().toString()))
                    // Filter out entries where the total size of files in the directory is less than or equal to 50,000 bytes.
                    .filter(e -> e.getValue().getSum() > 50_000)
                    // Print out the results: directory path, total size of files, and number of files.
                    .forEach(e -> {
                        System.out.printf("[%s] - %,d bytes, %d files %n",
                                e.getKey(),                     // Directory path
                                e.getValue().getSum(),          // Total size of files in bytes
                                e.getValue().getCount()         // Number of files
                        );
                    });
        } catch (IOException e) {
            // Print any IO exceptions that occur during file traversal.
            e.printStackTrace();
        }
    }
}
