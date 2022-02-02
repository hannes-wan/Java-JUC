package com.hannes.producerAndConsumer.JUCStyle;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
// 口诀：等待、业务、通知
public class Data {
    private int number = 0;

    Lock lock = new ReentrantLock();
    // 用condition替代wait方法和notifyAll方法
    Condition condition = lock.newCondition();

    // 等待
    // condition.await();
    // 唤醒全部
    // condition.signalAll();

    public void increment() throws InterruptedException {

        // 加锁
        lock.lock();
        try {
            // 判断
            while (number != 0) {
                condition.await();
            }

            // 业务
            number++;
            System.out.println(Thread.currentThread().getName() + " ---> " + number);

            // 通知
            condition.signalAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void decrement() throws InterruptedException {

        // 加锁
        lock.lock();
        try {
            // 判断
            while (number == 0) {
                condition.await();
            }

            // 业务
            number--;
            System.out.println(Thread.currentThread().getName() + " ---> " + number);

            // 通知
            condition.signalAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
