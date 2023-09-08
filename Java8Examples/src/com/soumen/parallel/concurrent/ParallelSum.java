package com.soumen.parallel.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ParallelSum {


    public static int parallelSum(List<Integer> inputs){
        int numWorkers = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(numWorkers);
        List<Future<Integer>> futures = new ArrayList<>();
        int chunkSize = (int) Math.ceil((double) inputs.size() / numWorkers);
        for(int i=0; i <numWorkers ;i++){
            int start = Math.min(i*chunkSize,inputs.size()-1);
            int end = Math.min((i+1)*chunkSize -1 , inputs.size()-1 );
            Callable<Integer> sumRequest = () -> { return performSum(inputs,start ,end); };
            futures.add(pool.submit(sumRequest));
        }
        int count = Thread.activeCount();
        // retrieve and accumulate results from futures
        int totalSize = 0;
        try {
            for (Future<Integer> f : futures)
                totalSize += f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        return totalSize;
    }

    public static int parallelExecutorServiceSum(List<Integer> inputs){
        int numWorkers = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(numWorkers);
        ExecutorCompletionService<Integer> ecs = new ExecutorCompletionService<>(pool);
        int chunkSize = (int) Math.ceil((double) inputs.size() / numWorkers);
        for(int i=0; i <numWorkers ;i++){
            int start = Math.min(i*chunkSize,inputs.size()-1);
            int end = Math.min((i+1)*chunkSize -1 , inputs.size()-1 );
            Callable<Integer> sumRequest = () -> { return performSum(inputs,start ,end); };
            ecs.submit(sumRequest);
        }
        int count = Thread.activeCount();
        // retrieve and accumulate results from futures
        int totalSize = 0;
        try {
            while(numWorkers >0){
                totalSize += ecs.take().get();
                numWorkers--;
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();

        return totalSize;
    }

    public static Integer performSum(List<Integer> inputs, int start, int end){
        int sum = 0;
        for(int i =start ; i <=end ;i++){
            sum = sum + inputs.get(i);
        }
        return sum;
    }

    public static void main(String[] args) {
        List<Integer> inputs = new ArrayList<>((Arrays. asList(1, 2, 3, 4, 5, 6,7,8,9,10,11,12)));
        System.out.println(parallelSum(inputs));
    }
}
