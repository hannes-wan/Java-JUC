package com.hannes.countDownLatchDemo;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // 总数是6
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " Go out");
                // 数量-1
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        // await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        // 等待计数器归零，await就会被唤醒，然后再继续向下执行
        countDownLatch.await();

        System.out.println("Close door");
    }
}
