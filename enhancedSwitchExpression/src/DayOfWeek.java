public class DayOfWeek {
    public static void printDayOfWeek(int day) {
        String nameOfTheDay = switch (day) {
            case 0 -> {yield "Sunday";} // this is what compiler implicitly transform all other cases in this example; in a code of block we need to explicitly use keyword yield that will cause switch expression to return value after yield;
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saturday";
            default -> "Invalid Day";
        };
        System.out.println(day + " - " + nameOfTheDay);
    }

    public static void main(String[] args) {
        printDayOfWeek(0);
        printDayOfWeek(1);
        printDayOfWeek(2);
        printDayOfWeek(3);
        printDayOfWeek(4);
        printDayOfWeek(5);
        printDayOfWeek(6);
        printDayOfWeek(7);
    }
}
