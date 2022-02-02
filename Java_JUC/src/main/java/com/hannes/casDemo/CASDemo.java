package com.hannes.casDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

// CAS: compareAndSet
// 乐观锁，版本号机制
public class CASDemo {
    public static void main(String[] args) {
        // 保证原子性的数字
        // AtomicInteger atomicInteger = new AtomicInteger(2020);

        // 原子引用避免ABA
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(2020, 1);

        // public final boolean compareAndSet(int expect, int update)
        // 期望，更新
        // 如果我期望的值达到了，那么就更新，否则就不更新
        // CAS是CPU的并发原语
        // Java无法操作内存，但Java可以调用C++，C++可以操作内存
        // native就是Java的后门，可以通过这个类操作内存


        // 这个compareAndSet，在底层其实是在操作内存，所以效率很高
        // atomicInteger.compareAndSet(2020, 2021);
        // System.out.println(atomicInteger.get());

        // ABA问题，最下面的操作其实已经被动过了
        // atomicInteger.compareAndSet(2021, 2020);
        // System.out.println(atomicInteger.get());

        // atomicInteger.compareAndSet(2020, 6666);
        // System.out.println(atomicInteger.get());

        new Thread(() -> {
            System.out.println("a1=>" + atomicStampedReference.getStamp());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Version + 1
            atomicStampedReference.compareAndSet(2020,2021,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);

            System.out.println("a2=>" + atomicStampedReference.getStamp());

            // Version + 1
            atomicStampedReference.compareAndSet(2021,2020,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);

            System.out.println("a3=>" + atomicStampedReference.getStamp());

        }, "a").start();

        new Thread(() -> {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("b1=>" + atomicStampedReference.getStamp());

            // Version + 1
            atomicStampedReference.compareAndSet(2020,6666,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);

            System.out.println("b2=>" + atomicStampedReference.getStamp());

        }, "b").start();
    }
}
