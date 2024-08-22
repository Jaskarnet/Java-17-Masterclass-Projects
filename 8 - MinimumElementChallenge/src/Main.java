import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[] array = readIntegers();
        System.out.println(Arrays.toString(array));
        System.out.println("Min value in array: " + findMin(array));

        int[] reversedCopy = reverseCopyArray(array);
        System.out.println("Original array after reverseCopyArray: " + Arrays.toString(array));
        System.out.println("Returned array after reverseCopyArray: " + Arrays.toString(reversedCopy));

        reverseArray(array);
        System.out.println("Original array after reverseArray: " + Arrays.toString(array));
    }

    private static int[] readIntegers() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter list of integers, separated by commas: ");
        String input = scanner.nextLine();
        String[] splits = input.split(",");
        int[] values = new int[splits.length];
        for (int i = 0; i < splits.length; i++) {
            values[i] = Integer.parseInt(splits[i].trim());
        }
        return values;
    }

    private static int findMin(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int el : array) {
            if (el < min) {
                min = el;
            }
        }
        return min;
    }

    private static void reverseArray(int[] array) {
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }

    private static int[] reverseCopyArray(int[] array) {
        int[] reversedArray = new int[array.length];
        int maxIndex = array.length - 1;
        for (int el : array)
            reversedArray[maxIndex--] = el;
        return reversedArray;
    }
}
