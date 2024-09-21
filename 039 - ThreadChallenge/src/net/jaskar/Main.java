package net.jaskar;

import java.util.concurrent.TimeUnit;

// My way
public class Main {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread myRunnableThread = new Thread(new MyRunnable());
        myThread.start();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        myRunnableThread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            myThread.interrupt();
            //myRunnableThread.interrupt();
        } catch (InterruptedException e) {
            System.out.println("Whoops! Main interrupted");
        }
        // Example of lambda passed to a Thread constructor (Runnable is a functional interface)
//        Thread lambdaThread = new Thread(() -> {
//            for (int i = 11; i <= 15; i++) {
//                System.out.println("LambdaThread: " + i);
//                try {
//                    TimeUnit.MILLISECONDS.sleep(500);
//                } catch (InterruptedException e) {
//                    System.out.println("Whoops! Lambda interrupted!");
//                }
//            }
//        });
//        lambdaThread.start();
    }
}

// Prints odd numbers; extends Thread
class MyThread extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i += 2) {
                System.out.println("OddThread: " + i);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("Whoops! OddThread interrupted!");
        }
    }
}

// Prints even numbers; implements Runnable
class MyRunnable implements Runnable {
    @Override
    public void run() {
        try {
            for (int i = 2; i <= 10; i += 2) {
                System.out.println("EvenRunnable: " + i);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("Whoops! EvenRunnable interrupted!");
        }
    }
}