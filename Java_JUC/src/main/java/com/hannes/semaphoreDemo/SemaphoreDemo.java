package com.hannes.semaphoreDemo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {

        // 线程数量：停车位3个
        // 限流作用
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    // acquire() 获取信号量
                    // 代表抢到了车位
                    // 如果已经满了，就等待到有线程被释放为止
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到车位");
                    // 停2秒
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + "离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // release() 释放信号量
                    // 代表离开车位
                    semaphore.release();
                }

            }, String.valueOf(i+1)).start();
        }

    }
}
