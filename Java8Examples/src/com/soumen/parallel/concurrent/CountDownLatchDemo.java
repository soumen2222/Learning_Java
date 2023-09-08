package com.soumen.parallel.concurrent; /**
 * Deciding how many bags of chips to buy for the party
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class CountDownLatchShopper extends Thread {

    public static int bagsOfChips = 1; // start with one on the list
    private static Lock pencil = new ReentrantLock();
    private static CountDownLatch fistBump = new CountDownLatch(5);

    public CountDownLatchShopper(String name) {
        this.setName(name);
    }

    public void run() {
        if (this.getName().contains("Olivia")) {
            pencil.lock();
            try {
                bagsOfChips += 3;
                System.out.println(this.getName() + " ADDED three bags of chips.");
            } finally {
                pencil.unlock();
            }
            fistBump.countDown();
        } else { // "Barron"
            try {
                fistBump.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pencil.lock();
            try {
                bagsOfChips *= 2;
                System.out.println(this.getName() + " DOUBLED the bags of chips.");
            } finally {
                pencil.unlock();
            }
        }
    }
}

public class CountDownLatchDemo {
    public static void main(String args[]) throws InterruptedException  {
        // create 10 shoppers: Barron-0...4 and Olivia-0...4
        CountDownLatchShopper[] shoppers = new CountDownLatchShopper[10];
        for (int i=0; i<shoppers.length/2; i++) {
            shoppers[2*i] = new CountDownLatchShopper("Barron-"+i);
            shoppers[2*i+1] = new CountDownLatchShopper("Olivia-"+i);
        }
        for (CountDownLatchShopper s : shoppers)
            s.start();
        for (CountDownLatchShopper s : shoppers)
            s.join();
        System.out.println("We need to buy " + CountDownLatchShopper.bagsOfChips + " bags of chips.");
    }
}