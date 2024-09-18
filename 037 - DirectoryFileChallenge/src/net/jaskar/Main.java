package net.jaskar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import java.util.StringJoiner;

// My way
public class Main {
    public static void main(String[] args) {
        Path publicFolderPath = Path.of("public");
        createIndexFiles(publicFolderPath);
    }

    public static void createIndexFiles(Path rootDirectory) {
        String indexFileName = "index.txt";
        if (Files.isDirectory(rootDirectory)) {
            try (var children = Files.list(rootDirectory)) {
                var childrenList = children.toList();
                childrenList.stream()
                        .filter(Files::isDirectory)
                        .forEach(Main::createIndexFiles);

                Path indexPath = rootDirectory.resolve(indexFileName);
                // Delete existing index file
                Files.deleteIfExists(indexPath);
                // Copy existing index file from one of subfolders
                childrenList.stream()
                        .filter(Files::isDirectory)
                        .limit(1)
                        .forEach(child -> {
                                    Path childIndexPath = child.resolve(indexFileName);
                                    if (Files.exists(childIndexPath)) {
                                        try {
                                            Files.copy(childIndexPath, indexPath, StandardCopyOption.REPLACE_EXISTING);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                        );
                // Append every child to index file
                StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
                Instant currentTime = Instant.now();
                if (Files.exists(indexPath)) {
                    stringJoiner.add(System.lineSeparator());
                }
                childrenList.forEach(p -> stringJoiner.add(p.toString() + " " + currentTime.atZone(ZoneId.systemDefault())));
                if (Files.exists(indexPath)) {
                    Files.writeString(indexPath, stringJoiner.toString(), StandardOpenOption.APPEND);
                } else {
                    Files.writeString(indexPath, stringJoiner.toString());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
