package com.hannes.future;

import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 异步调用：Ajax
 *      异步执行
 *      成功回调
 *      失败回调
 */
public class AsynchronousCallDemo {

    @Test
    public void test1() throws InterruptedException {
        System.out.println("111");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("222");
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        // 发起一个请求
        // Void类代表没有返回值，调用时应该调用runAsync
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "runAsync => void");
        });

        System.out.println("111");

        // 获取阻塞执行结果，也就是执行以上void方法的结果，不调用就不执行了
        // 也可以使用 completableFuture.join();
        // 如果打印completableFuture.join()，就会显示null，因为没有返回值
        completableFuture.get();
    }

    @Test
    public void test3() throws ExecutionException, InterruptedException {
        // 有返回值时，调用时应该调用supplyAsync
        // 返回的是错误信息
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {

            System.out.println(Thread.currentThread().getName() + "supplyAsync => Integer");
            return 1024;
        });

        // t是异步回调的返回值
        // u是错误信息
        // 如果没有错误，u会是null
        // 如果有错误，t会是null，u会变成错误信息
        System.out.println(completableFuture.whenComplete((t, u) -> {
            System.out.println("t = " + t + "\nu = " + u);
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            // 返回失败
            return 233;
        }));
    }

    @Test
    public void test4() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(1000L);
                return "test";
            } catch (Exception e){
                return "failed test";
            }
        });

        // 调用complete可以主动终结计算过程并输出相关结果
        future.complete("manual test");
        System.out.println(future.join());
    }

    @Test
    // 拿取回调的返回值
    public void test5() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            System.out.println("compute test");
            return "test";
        });

        // 调用join可以拿到返回值
        String result = future.join();
        System.out.println("get result: " + result);
    }

    @Test
    // Void执行
    public void test6() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
            System.out.println("compute test");
        });
        System.out.println("get result: " + future.join());
    }

    @Test
    // thenApply连接
    public void test7() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(()->{
            System.out.println("compute 1");
            return 1;
        });
        CompletableFuture<Integer> future2 = future1.thenApply((p)->{
            System.out.println("compute 2");
            return p+10;
        });
        System.out.println("result: " + future2.join());
    }

    @Test
    // thenCombine，用于没有前后依赖关系的任务的连接
    // 同前面一组连接函数相比
    // thenCombine最大的不同是连接任务可以是一个独立的CompletableFuture（或者是任意实现了CompletionStage的类型）
    // 从而允许前后连接的两个任务可以并行执行（后置任务不需要等待前置任务执行完成）
    public void test8() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(()->{
            System.out.println("compute 1");
            return 1;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(()->{
            System.out.println("compute 2");
            return 10;
        });
        CompletableFuture<Integer> future3 = future1.thenCombine(future2, (r1, r2) -> r1 + r2);
        System.out.println("result: " + future3.join());
    }

    @Test
    // 用于有依赖关系的任务的连接
    public void test9() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(()->{
            System.out.println("compute 1");
            return 1;
        });
        CompletableFuture<Integer> future2 = future1.thenCompose((r)->CompletableFuture.supplyAsync(()->r+10));
        System.out.println(future2.join());
    }

    @Test
    // handler
    // handle与whenComplete的作用有些类似，但是handle接收的处理函数有返回值，而且返回值会影响最终获取的计算结果
    public void test10() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(()->{
            System.out.println("compute 1");
            return 1;
        });
        CompletableFuture<Integer> future2 = future1.handle((r, e)->{
            if(e != null){
                System.out.println("compute failed!");
                return r;
            } else {
                System.out.println("received result is " + r);
                return r + 10;
            }
        });
        System.out.println("result: " + future2.join());
    }
}
