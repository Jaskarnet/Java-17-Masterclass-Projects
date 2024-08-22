public class Customer {
    String name;
    String emailAddress;
    double creditLimit;

    public Customer(String name, String emailAddress, double creditLimit) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.creditLimit = creditLimit;
    }

    public Customer(String name, String emailAddress) {
        this(name, emailAddress, 1000.00d);
    }

    public Customer() {
        this("Jan", "jankowalski@gmail.com");
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", creditLimit=" + creditLimit +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public double getCreditLimit() {
        return creditLimit;
    }
}
