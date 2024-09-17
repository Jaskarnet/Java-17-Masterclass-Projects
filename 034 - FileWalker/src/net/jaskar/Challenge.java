package net.jaskar;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Challenge {
    public static void main(String[] args) {
        // Start traversal from the parent directory (..)
        Path startingPath = Path.of("..");

        // Create a new StatsVisitor instance with a maximum print level
        FileVisitor<Path> statsVisitor = new StatsVisitor(Integer.MAX_VALUE);

        // Walk the file tree, starting from the given path
        try {
            Files.walkFileTree(startingPath, statsVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Custom FileVisitor class extending SimpleFileVisitor to track folder sizes,
     * number of files, and number of subfolders.
     */
    private static class StatsVisitor extends SimpleFileVisitor<Path> {
        // Enum to represent the keys used in the folderSizes map
        private enum FolderAttributes {
            SIZE, FILES, SUBFOLDERS
        }
        private Path initialPath = null;  // The root directory from where traversal starts
        private Map<Path, Map<FolderAttributes, Long>> folderSizes = new LinkedHashMap<>(); // Store sizes, file counts, and subfolder counts per directory
        private int initialCount; // Depth of the starting directory
        private final int printLevel; // Level limit for printing folder info

        // Constructor to set the print level
        public StatsVisitor(int printLevel) {
            this.printLevel = printLevel;
        }

        /**
         * This method is called for each file visited. It updates the size and file count for the parent directory.
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Objects.requireNonNull(file);
            Objects.requireNonNull(attrs);

            // Update the folderSizes map for the parent folder of this file
            folderSizes.merge(file.getParent(), new HashMap<>(), (o, n) -> {
                o.merge(FolderAttributes.SIZE, 0L, (o1, n1) -> o1 + attrs.size()); // Increment total size
                o.merge(FolderAttributes.FILES, 0L, (o1, n1) -> o1 + 1); // Increment file count
                return o;
            });
            return FileVisitResult.CONTINUE;
        }

        /**
         * This method is called when visiting a directory before its entries are visited.
         */
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Objects.requireNonNull(dir);
            Objects.requireNonNull(attrs);

            if (initialPath == null) {
                // Set the initial path and depth if this is the first directory visited
                initialPath = dir;
                initialCount = dir.getNameCount();
            } else {
                // Calculate the relative depth of the directory from the initial path
                int relativeLevel = dir.getNameCount() - initialCount;

                if (relativeLevel == 1) {
                    // Clear the folderSizes map when entering a new top-level directory
                    folderSizes.clear();
                } else {
                    // Increment the subfolder count for the parent directory
                    folderSizes.merge(dir.getParent(), new HashMap<>(), (o, n) -> {
                        o.merge(FolderAttributes.SUBFOLDERS, 0L, (o1, n1) -> o1 + 1);
                        return o;
                    });
                }

                // Initialize the current directory in the map with size, files, and subfolders set to 0
                folderSizes.put(dir, new HashMap<>(Map.of(
                        FolderAttributes.SIZE, 0L,
                        FolderAttributes.FILES, 0L,
                        FolderAttributes.SUBFOLDERS, 0L
                )));
            }
            return FileVisitResult.CONTINUE;
        }

        /**
         * This method is called after all entries in a directory have been visited.
         */
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Objects.requireNonNull(dir);

            if (dir.equals(initialPath)) {
                // Stop traversal if we have reached the initial directory again
                return FileVisitResult.TERMINATE;
            }

            // Calculate the relative level of the directory from the initial path
            int relativeLevel = dir.getNameCount() - initialCount;

            if (relativeLevel == 1) {
                // If we are back at a top-level directory, print the stats for the current directory and its subdirectories
                folderSizes.forEach((key, value) -> {
                    int level = key.getNameCount() - initialCount - 1;
                    if (level < printLevel) {
                        System.out.printf("%s[%s] - %,d bytes, %d files, %d subfolders %n",
                                "\t".repeat(level),
                                key.getFileName(),
                                value.get(FolderAttributes.SIZE),  // Print total size
                                value.get(FolderAttributes.FILES), // Print number of files
                                value.get(FolderAttributes.SUBFOLDERS)); // Print number of subfolders
                    }
                });
            } else {
                // Propagate the folder statistics to the parent directory
                long folderSize = folderSizes.get(dir).get(FolderAttributes.SIZE);
                long subfolders = folderSizes.get(dir).get(FolderAttributes.SUBFOLDERS);
                long files = folderSizes.get(dir).get(FolderAttributes.FILES);

                // Update the parent's stats by adding the current folder's stats
                folderSizes.merge(dir.getParent(), new HashMap<>(), (o, n) -> {
                    o.merge(FolderAttributes.SIZE, 0L, (o1, n1) -> o1 + folderSize);
                    o.merge(FolderAttributes.FILES, 0L, (o1, n1) -> o1 + files);
                    o.merge(FolderAttributes.SUBFOLDERS, 0L, (o1, n1) -> o1 + subfolders);
                    return o;
                });
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
