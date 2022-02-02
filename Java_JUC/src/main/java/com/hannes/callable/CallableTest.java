package com.hannes.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // new Thread(new FutureTask<V>(Callable)).start();
        // FutureTask是一个适配类
        FutureTask futureTask = new FutureTask(new MyThread());

        // 执行线程
        new Thread(futureTask, "A").start();
        // 启动两个线程但是只会执行一次call()，因为结果会被缓存，效率高
        new Thread(futureTask, "B").start();

        // 拿到最后的返回值
        // 这个get方法可能会产生阻塞（因为方法可能要执行很多东西），所以把它放到最后，或者使用异步通信处理
        int result = (int) futureTask.get();
        System.out.println(result);

    }
}
