package com.hannes.eightLock.lockDemo01;

import java.util.concurrent.TimeUnit;

public class Phone {
    // synchronized 锁的对象是方法的调用者
    // 所以锁的是phone
    // 这里两个方法用的是同一个锁，所以谁先拿到谁执行
    public synchronized void sendMessage() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Send message");
    }

    public synchronized void call() {
        System.out.println("Call");
    }
}
