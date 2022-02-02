package com.hannes.blockingQueue;

import org.testng.annotations.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {

    /**
     * 抛出异常:
     *      (1) 如果队列满了
     *      抛出 java.lang.IllegalStateException: Queue full
     *      (2) 如果队列空
     *      抛出 java.util.NoSuchElementException
     *      (3) 查看队首
     *      抛出 java.util.NoSuchElementException
     *
     */
    @Test
    public void test1() {

        // 队列的大小设置为3
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        System.out.println(queue.add("A"));
        System.out.println(queue.add("B"));
        System.out.println(queue.add("C"));
        // System.out.println(queue.add("D"));

        // 查看队首元素
        System.out.println(queue.element());

        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());

        // 查看队首元素
        System.out.println(queue.element());
    }

    /**
     * offer不抛出异常，返回true或者false
     * pool不抛出异常，返回数值或null
     * peek不抛出异常，返回数值或null
     */
    @Test
    public void test2() {
        // 队列的大小设置为3
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        // 添加
        System.out.println(queue.offer("A"));
        System.out.println(queue.offer("B"));
        System.out.println(queue.offer("C"));
        System.out.println(queue.offer("D"));

        // 查看队首元素
        System.out.println(queue.peek());

        // 弹出
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        // 查看队首元素
        System.out.println(queue.peek());
    }

    /**
     * 等待，一直阻塞
     * put、take都会一直阻塞
     */
    @Test
    public void test3() throws InterruptedException {
        // 队列的大小设置为3
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        // 添加
        queue.put("A");
        queue.put("B");
        queue.put("C");
        // queue.put("D");

        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        // System.out.println(queue.take());
    }

    /**
     * 超时等待
     */
    @Test
    public void test4() throws InterruptedException {
        // 队列的大小设置为3
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        // 添加
        System.out.println(queue.offer("A",3, TimeUnit.SECONDS));
        System.out.println(queue.offer("B",3, TimeUnit.SECONDS));
        System.out.println(queue.offer("C",3, TimeUnit.SECONDS));
        System.out.println(queue.offer("D",3, TimeUnit.SECONDS));

        // 查看队首元素
        System.out.println(queue.peek());

        // 弹出
        System.out.println(queue.poll(3, TimeUnit.SECONDS));
        System.out.println(queue.poll(3, TimeUnit.SECONDS));
        System.out.println(queue.poll(3, TimeUnit.SECONDS));
        System.out.println(queue.poll(3, TimeUnit.SECONDS));

        // 查看队首元素
        System.out.println(queue.peek());
    }
}
