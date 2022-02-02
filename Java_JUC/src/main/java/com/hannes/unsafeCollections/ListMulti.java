package com.hannes.unsafeCollections;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListMulti {
    public static void main(String[] args) {
        // 证明了并发下ArrayList的不安全性
        List<String> list = new ArrayList<>();

        /**
         * 解决方案：
         *      (1) 用Vector替代
         *      List<String> list = new Vector<>();
         *
         *      (2) 用Collections.synchronizedList替代
         *      List<String> list = Collections.synchronizedList(new ArrayList<>);
         *
         *      (3) 用CopyOnWriteArrayList
         *      List<String> list = new CopyOnWriteArrayList<>();
         *      原理：
         *          写入时复制
         *          多个线程调用时，List读取的时候是固定的
         *          写入的时候，避免覆盖造成数据问题
         *
         * CopyOnWriteArrayList比Vector牛逼在哪里：
         *      Vector用的是synchronized
         *      CopyOnWriteArrayList用的是ReentrantLock
         */

        // 10个线程来跑
        // 会抛出java.util.ConcurrentModificationException 并发修改异常
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
