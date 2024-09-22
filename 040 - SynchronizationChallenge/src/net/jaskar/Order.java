package net.jaskar;

public record Order(int id, String shoeType, int quantity) {
    private static int LAST_ID = 1;

    public Order {
        id = LAST_ID++;
    }

    public Order(String shoeType, int quantity) {
        this(0, shoeType, quantity);
    }
}
