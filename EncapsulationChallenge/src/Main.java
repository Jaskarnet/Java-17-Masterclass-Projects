public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer(50, true);
        System.out.println("Initial page count = " + printer.getPagesPrinted());

        int pagesPrinted = printer.printPages(5);
        System.out.println("Current job pages: " + pagesPrinted +
                ", printer total: " + printer.getPagesPrinted());

        pagesPrinted = printer.printPages(10);
        System.out.println("Current job pages: " + pagesPrinted +
                ", printer total: " + printer.getPagesPrinted());
    }
}
