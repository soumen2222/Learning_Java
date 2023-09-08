package com.soumen.parallel.concurrent; /**
 * Deciding how many bags of chips to buy for the party
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BarrierShopper extends Thread {

    public static int bagsOfChips = 1; // start with one on the list
    private static Lock pencil = new ReentrantLock();
    private static CyclicBarrier fistBump = new CyclicBarrier(10);

    public BarrierShopper(String name) {
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
            try {
                fistBump.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        } else { // "Barron"
            try {
                fistBump.await();
            } catch (InterruptedException | BrokenBarrierException e) {
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

public class BarrierDemo {
    public static void main(String args[]) throws InterruptedException  {
        // create 10 shoppers: Barron-0...4 and Olivia-0...4
        BarrierShopper[] shoppers = new BarrierShopper[10];
        for (int i=0; i<shoppers.length/2; i++) {
            shoppers[2*i] = new BarrierShopper("Barron-"+i);
            shoppers[2*i+1] = new BarrierShopper("Olivia-"+i);
        }
        for (BarrierShopper s : shoppers)
            s.start();
        for (BarrierShopper s : shoppers)
            s.join();
        System.out.println("We need to buy " + BarrierShopper.bagsOfChips + " bags of chips.");
    }
}