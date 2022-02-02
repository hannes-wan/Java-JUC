package com.hannes.eightLock.lockDemo02;

import java.util.concurrent.TimeUnit;

// 反射对象.class全局唯一
public class Phone {

    // synchronized 锁的对象是方法的调用者
    // 所以锁的是phone
    // 这里两个方法用的是同一个锁，所以谁先拿到谁执行

    // 如果这里synchronized前加个static
    // 就是静态方法，类一加载就有了，是个模版
    // 如果有static，锁的其实是唯一的反射.class对象
    // 这时候无论几个对象，都是先发短信后打电话，因为锁住的是唯一的Class
    public synchronized void sendMessage() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Send message");
    }

    public synchronized void call() {
        System.out.println("Call");
    }

    // 普通方法，不受锁的影响
    public void printHello() {
        System.out.println("Hello");
    }
}
