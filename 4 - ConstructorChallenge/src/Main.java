public class Main {
    public static void main(String[] args) {
        Customer customer1 = new Customer("Piotr", "Zbychulski", 321);
        Customer customer2 = new Customer("Jarosław", "Mazowiecki");
        Customer customer3 = new Customer();
        System.out.println(customer1 + "\n" + customer2 + "\n" + customer3);
    }
}
