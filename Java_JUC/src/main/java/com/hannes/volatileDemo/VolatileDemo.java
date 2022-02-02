package com.hannes.volatileDemo;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {

    // private static volatile int num = 0;
    private static volatile AtomicInteger num = new AtomicInteger();

    public static void main(String[] args) {

        // 理论上能够达到20000，但实际不行
        // 证明了volatile的非原子性
        // 如果是对add方法加锁，那确实能到20000，但是太笨重
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    add();
                }
            }).start();
        }

        System.out.println(Thread.currentThread().getName() + "\t" + num);
    }

    public static void add() {
        // ++是一个一个非原子性操作，一个++中间有很多步骤
        // 可以用原子类的AtomicInteger()替代++，防止非原子性发生
        // num++;
        // 这个是 + 1，代替 ++，CAS
        num.getAndIncrement();
    }
}
