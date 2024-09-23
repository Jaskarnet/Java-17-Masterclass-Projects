package net.jaskar;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static net.jaskar.ShoeWarehouse.PRODUCT_LIST;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        ShoeWarehouse shoeWarehouse = new ShoeWarehouse();
        ExecutorService orderingService = Executors.newCachedThreadPool();

//        Callable<Order> orderingTask = () -> {
//            Order newOrder = generateOrder();
//            shoeWarehouse.receiveOrder(newOrder);
//            return newOrder;
//        };
//
//        List<Callable<Order>> tasks = Collections.nCopies(15, orderingTask);
//        try {
//            orderingService.invokeAll(tasks);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        try {
            for (int j = 0; j < 15; j++) {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(50, 2000));
                orderingService.execute(() -> shoeWarehouse.receiveOrder(generateOrder()));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        orderingService.shutdown();
        try {
            orderingService.awaitTermination(6, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        shoeWarehouse.shutdown();
    }

    // old main
    public static void threadMain(String[] args) {
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

    private static Order generateOrder() {
        return new Order(
                PRODUCT_LIST[random.nextInt(0, PRODUCT_LIST.length)],
                random.nextInt(1, 4));
    }
}
