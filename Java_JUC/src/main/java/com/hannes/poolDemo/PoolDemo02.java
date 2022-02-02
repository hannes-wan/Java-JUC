package com.hannes.poolDemo;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolDemo02 {
    public static void main(String[] args) {

        /**
         * 拒绝策略：
         *      // 如果池和等待队列都满了，还有线程进来，不处理，直接抛出异常
         *      new ThreadPoolExecutor.AbortPolicy());
         *      // 哪来的去哪里执行
         *      new ThreadPoolExecutor.CallerRunsPolicy());
         *      // 队列满了直接丢掉任务，也不抛出异常
         *      new ThreadPoolExecutor.DiscardPolicy());
         *      // 队列满了，尝试去和最早的线程竞争，失败了就不执行，也不抛出异常
         *      new ThreadPoolExecutor.DiscardOldestPolicy());
         */

        /**
         *
         * 定义线程的数量：
         * CPU密集：
         *      定义：
         *          几核的CPU就是几，可以保持CPU效率最高
         *          用Runtime.getRuntime().availableProcessors()可以直到CPU核心数
         * I/O密集：
         *      定义：
         *          IO密集型指的是系统的CPU性能相对硬盘、内存要好很多
         *          此时系统运作，大部分的状况是CPU在等I/O (硬盘/内存) 的读/写操作，此时CPU Loading并不高
         *          判断程序中十分耗I/O的线程，让这个数量 * 2
         */
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,        // 核心的池大小，在没有任务需要执行的时候线程池的大小
                5,                      // 最大的池大小，如果队列中任务已满，就会创建新的线程来执行任务（总并行线程数不会超过最大池大小）
                3,                      // 设置从最大池里面取出的空间如果在三秒内不被使用，就会关闭这个空间
                TimeUnit.SECONDS,       // 时间单位
                new LinkedBlockingDeque<>(3),     // 等待队列，最多有3个线程在等待进入池
                Executors.defaultThreadFactory(),           // 默认的线程工厂（一般不去动它）
                new ThreadPoolExecutor.CallerRunsPolicy());  // 队列满了，尝试去和最早的线程竞争，失败了就不执行，也不抛出异常

        try {
            // 最大承载数 = maximumPoolSize + LinkedBlockingDeque.size() = 5 + 3 = 8
            // 超出后抛出 java.util.concurrent.RejectedExecutionException
            for (int i = 0; i < 20; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " OK ");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
