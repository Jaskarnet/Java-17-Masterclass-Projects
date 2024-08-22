import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nextNumber = "";
        double number = 0.0d;
        double minNumber = Double.MIN_VALUE;
        double maxNumber = Double.MAX_VALUE;
        boolean areNumbersInitialized = false;

        while (true) {
            System.out.print("Enter a number, or enter any character to quit: ");
            nextNumber = scanner.nextLine();
            try {
                number = Double.parseDouble(nextNumber);
                if (!areNumbersInitialized) {
                    minNumber = number;
                    maxNumber = number;
                    areNumbersInitialized = true;
                } else if (number < minNumber)
                    minNumber = number;
                else if (number > maxNumber)
                    maxNumber = number;
            } catch (NumberFormatException e) {
                if (areNumbersInitialized) System.out.println("Min value entered: " + minNumber + " Max value entered: " + maxNumber);
                System.out.println("Exiting...");
                break;
            }
        }
    }
}
