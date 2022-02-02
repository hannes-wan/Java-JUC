package com.hannes.volatileDemo;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 因为内存不同步，所以A线程拿不到Main线程修改的num
// 程序不知道主内存的值已经被修改过了，所以运行后不会停止
public class JMMDemo {

    // 如果不加volatile，程序就会死循环
    // 加了volatile可以保证可见行 -> 被所有线程可见
    private volatile static int num = 0;

    public static void main(String[] args) throws InterruptedException {    // Main线程
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5,
                3, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        try {
            // A线程，对主内存的变化（Main线程改变自己线程的工作内存，然后再写到主内存的）不知道（不可见）
            // 如果这个字段加了volatile，就获得了被线程看到的可见行
            threadPool.execute(() -> {
                while (num == 0) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

        TimeUnit.SECONDS.sleep(1);

        num = 1;
        System.out.println(num);
    }
}
