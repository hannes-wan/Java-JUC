package com.hannes.producerAndConsumer.sequential;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private int number = 1;

    Lock lock = new ReentrantLock();
    // 用condition替代wait方法和notifyAll方法
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void printA(){
        lock.lock();
        try {
            while (number != 1) {
                // 等待
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + "--->AAAAA");

            // 唤醒指定的人 -> B
            number = 2;
            condition2.signal();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB(){
        lock.lock();
        try {
            while (number != 2) {
                // 等待
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "--->BBBBB");

            // 唤醒指定的人 -> C
            number = 3;
            condition3.signal();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC(){
        lock.lock();
        try {
            while (number != 3) {
                // 等待
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "--->CCCCC");

            // 唤醒指定的人 -> A
            number = 1;
            condition1.signal();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
