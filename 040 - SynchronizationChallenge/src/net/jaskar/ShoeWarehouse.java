package net.jaskar;

import java.util.*;

public class ShoeWarehouse {
    public final static String[] PRODUCT_LIST = {"Running Shoes", "Sandals", "Boots", "Slippers", "High Tops"};
    private final List<Order> orderList = new LinkedList<>();

    public synchronized void receiveOrder(Order order) {
        while (orderList.size() > 20) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        orderList.add(order);
        System.out.println("Incoming: " + order);
        notifyAll();
    }

    public synchronized void fulfillOrder() {
        while (orderList.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Order order = orderList.remove(0);
        System.out.println(Thread.currentThread().getName() + " Fulfilled: " + order);
        notifyAll();
    }
}
