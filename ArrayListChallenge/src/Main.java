import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> groceryList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        Boolean stopCondition = false;
        while (!stopCondition) {
            printActions(groceryList);
            String input = scanner.nextLine().trim();
            try {
                int option = Integer.parseInt(input);
                switch (option) {
                    case 0 -> stopCondition = true;
                    case 1 -> addItems(scanner, groceryList);
                    case 2 -> removeItems(scanner, groceryList);
                }
                groceryList.sort(Comparator.naturalOrder());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid choice.");
            }
        }
    }

    private static void printActions(ArrayList<String> groceryList) {
        // Define total line width for alignment
        final int totalWidth = 60;

        StringBuilder output = new StringBuilder();
        output.append("""
        ╔════════════════════════════════════════════════════════╗
        ║                   GROCERY LIST APP                     ║
        ╠════════════════════════════════════════════════════════╣
        """);

        // Join the grocery list into a single string
        String groceryLine = String.join(", ", groceryList);

        // Break the grocery list into lines of the appropriate width
        int lineStart = 0;
        while (lineStart < groceryLine.length()) {
            // Determine the end of the current line
            int lineEnd = Math.min(lineStart + totalWidth - 6, groceryLine.length());

            // Adjust to avoid splitting words by moving lineEnd backward
            if (lineEnd < groceryLine.length() && groceryLine.charAt(lineEnd) != ' ') {
                int lastSpace = groceryLine.lastIndexOf(", ", lineEnd);
                if (lastSpace >= lineStart) {
                    lineEnd = lastSpace + 1; // Include the comma and space
                }
            }

            // Extract the current line segment
            String lineSegment = groceryLine.substring(lineStart, lineEnd).trim();

            // Append the line segment to the output
            output.append("║ ").append(lineSegment);

            // Calculate padding needed to align the vertical bar
            int padding = totalWidth - lineSegment.length() - 5; // Subtracting 3 for "║ " and trailing "║"
            if (padding > 0) {
                output.append(" ".repeat(padding));
            }

            output.append("║\n");

            // Move the start to the next segment
            lineStart = lineEnd;
        }

        output.append("""
        ╠════════════════════════════════════════════════════════╣
        ║                                                        ║
        ║ Available actions:                                     ║
        ║                                                        ║
        ║ 0 - to shutdown                                        ║
        ║ 1 - to add item(s) to list (comma delimited list)      ║
        ║ 2 - to remove any items (comma delimited list)         ║
        ║                                                        ║
        ╚════════════════════════════════════════════════════════╝
        Enter a number for which action you want to do:""");
        output.append(" ");

        System.out.print(output.toString());
    }



    private static void addItems(Scanner scanner, ArrayList<String> groceryList) {
        System.out.print("Add item(s) [separate items by comma]: ");
        String input = scanner.nextLine();
        ArrayList<String> items = new ArrayList<>(List.of(input.split(",")));
        for (String item : items)
            if (!groceryList.contains(item.trim())) groceryList.add(item.trim());
    }

    private static void removeItems(Scanner scanner, ArrayList<String> groceryList) {
        System.out.println("Remove item(s) [separate items by comma]: ");
        String input = scanner.nextLine();
        ArrayList<String> items = new ArrayList<>(List.of(input.split(",")));
        for (String item : items)
            groceryList.remove(item.trim());
    }
}















