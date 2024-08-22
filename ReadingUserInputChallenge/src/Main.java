import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        for (int count = 1; count <= 5; count++) {
            int number = 0;
            boolean isValidNumber = false;
            do {
                try {
                    System.out.print("Enter number #" + count + ": ");
                    number = Integer.parseInt(scanner.nextLine());
                    isValidNumber = true;
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid number!");
                }
            } while (!isValidNumber);
            numbers.add(number);
            sum += number;
            System.out.println(numbers.get(count - 1));
        }
        System.out.println("The sum of those five numbers is: " + sum);
    }
}
