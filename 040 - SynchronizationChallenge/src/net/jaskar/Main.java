package net.jaskar;

import java.util.Random;

import static net.jaskar.ShoeWarehouse.PRODUCT_LIST;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        ShoeWarehouse shoeWarehouse = new ShoeWarehouse();
        Thread producerThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shoeWarehouse.receiveOrder(new Order(
                        PRODUCT_LIST[random.nextInt(0, PRODUCT_LIST.length)],
                        random.nextInt(1, 4)));
            }
        });
        producerThread.start();
        for (int i = 0; i < 2; i++) {
            Thread consumerThread = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    shoeWarehouse.fulfillOrder();
                }
            });
            consumerThread.start();
        }
    }
}
