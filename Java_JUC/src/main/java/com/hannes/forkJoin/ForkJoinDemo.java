package com.hannes.forkJoin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 求和计算任务
 * 使用ForkJoin步骤：
 *      1. 通过ForkJoinPool执行
 *      2. extends RecursiveAction或者RecursiveTask
 *      2. 计算任务 forkJoinPool.execute(ForkJoinTask task)，实现compute()计算接口
 *
 * ForkJoinTask子类：
 *      CounterComplete
 *      RecursiveAction     递归事件，没有返回值
 *      RecursiveTask       递归任务，有返回值
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForkJoinDemo extends RecursiveTask<Long> {

    private Long start;
    private Long end;

    // 临界值
    private Long temp = 10000L;

    public ForkJoinDemo (Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    // 计算方法
    protected Long compute() {
        if ((end - start) < temp) {
            Long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            // 中间值
            Long middle = (start + end) / 2;

            // 拆分任务
            ForkJoinDemo task1 = new ForkJoinDemo(start, middle);
            // 把任务压入线程队列
            task1.fork();
            // 拆分任务
            ForkJoinDemo task2 = new ForkJoinDemo(middle+1, end);
            task2.fork();

            return task1.join() + task2.join();
        }
    }
}
