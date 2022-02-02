package com.hannes.eightLock.lockDemo01;

import java.util.concurrent.TimeUnit;

public class UsePhone {
    public static void main(String[] args) {
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
        }, "A").start();
    }
}
