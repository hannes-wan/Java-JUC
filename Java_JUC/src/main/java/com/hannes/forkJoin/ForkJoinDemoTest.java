package com.hannes.forkJoin;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

// Stream流效率可以达到ForkJoin的10倍
public class ForkJoinDemoTest {
    @Test
    // ForkJoin
    public void test1() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // execute 没有结果
        // submit 有结果
        // forkJoinPool.execute(task);
        ForkJoinTask<Long> submit = forkJoinPool.submit(new ForkJoinDemo(0L, 10_0000_0000L));
        Long sum = submit.get();

        long end = System.currentTimeMillis();
        System.out.println("sum = " + sum + "\t时间：" + (end - start));
    }

    @Test
    // Stream
    public void test2() {
        long start = System.currentTimeMillis();

        // Stream并行流
        // range ( , )     rangeClose ( , ]
        // Long::sum是一个函数式接口
        long sum = LongStream
                .rangeClosed(0L, 10_0000_0000L)
                .parallel()
                .reduce(0, Long::sum);

        long end = System.currentTimeMillis();
        System.out.println("sum = " + sum + "时间：" + (end - start));
    }
}
