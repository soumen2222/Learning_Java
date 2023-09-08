package com.soumen.parallel.concurrent; /**
 * Recursively sum a range of numbers
 */

import com.soumen.parallel.concurrent.RecursiveSum;

import java.util.concurrent.*;


public class DivideAndConquerDemo {
    public static void main(String args[]) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        Long total = pool.invoke(new RecursiveSum(0, 1_000_000_000));
        pool.shutdown();
        System.out.println("Total sum is " + total);
    }
}