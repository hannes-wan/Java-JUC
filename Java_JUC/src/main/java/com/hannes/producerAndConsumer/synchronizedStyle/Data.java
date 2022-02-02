package com.hannes.producerAndConsumer.synchronizedStyle;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
// 口诀：等待、业务、通知
public class Data {
    private int number = 0;

    public synchronized void increment() throws InterruptedException {
        while (number != 0) {
            // 等待操作
            wait();
        }

        // 业务
        number++;
        System.out.println(Thread.currentThread().getName() + " ---> " + number);

        // 通知其他线程，我+1完毕了
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        while (number == 0) {
            // 等待操作
            wait();
        }

        // 业务
        number--;
        System.out.println(Thread.currentThread().getName() + " ---> " + number);

        // 通知其他线程，我-1完毕了
        this.notifyAll();
    }
}
