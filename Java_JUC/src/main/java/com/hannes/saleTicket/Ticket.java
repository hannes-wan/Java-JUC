package com.hannes.saleTicket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private int number = 50;

    Lock lock = new ReentrantLock();

    // 这里加synchronized可以同步，但是不好
    // 用lock锁也可以保证同步
    public void sale(){

        // 加锁
        lock.lock();

        try {
            // 业务代码
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了：" + (number--) + "张票\t剩余：" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 解锁
            lock.unlock();
        }
    }
}
