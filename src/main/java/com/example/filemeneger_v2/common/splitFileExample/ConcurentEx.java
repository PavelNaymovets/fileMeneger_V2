package com.example.filemeneger_v2.common.splitFileExample;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurentEx {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService thr = Executors.newFixedThreadPool(5);
        CountDownLatch cdl = new CountDownLatch(50);
        for (int i = 0; i < 50; i++) {
            thr.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                cdl.countDown();
            });
        }

        thr.shutdown();

        cdl.await();
        long finish = System.currentTimeMillis();


        System.out.println((finish - start));
    }
}
