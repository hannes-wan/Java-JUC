package com.hannes.eightLock.lockDemo02;

import java.util.concurrent.TimeUnit;

public class UsePhone {
    public static void main(String[] args) {

        // 如果是两个对象，那就是先打电话
        // 因为synchronized锁的对象不一样
        Phone phone = new Phone();

        // 因为锁的存在，senMessage是被先调用的
        new Thread(() -> {
            phone.sendMessage();
        }, "A").start();

        try {
            // 是对Thread.sleep方法的包装，实现是一样的，只是多了时间单位转换和验证
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone.call();
        }, "B").start();

        new Thread(() -> {
            phone.printHello();
        }, "C").start();
    }
}
