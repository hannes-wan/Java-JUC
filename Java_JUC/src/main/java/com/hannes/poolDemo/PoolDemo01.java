package com.hannes.poolDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 把Executors当做一个工具类，三大方法
public class PoolDemo01 {
    public static void main(String[] args) {
        /*
          创建单个线程
          Executors.newSingleThreadExecutor();
          创建一个固定的线程池大小
          Executors.newFixedThreadPool(5);
          可伸缩的，遇强则强，遇弱则弱
          Executors.newCachedThreadPool();
         */

        // ExecutorService threadPool = Executors.newSingleThreadExecutor();
        // ExecutorService threadPool = Executors.newFixedThreadPool(5);
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> System.out.println(Thread.currentThread().getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
