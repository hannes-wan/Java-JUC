package com.hannes.singleDemo;

// 懒汉式单例
// 单线程不安全
public class LazyDemo {

    private LazyDemo() {}

    // 为了避免指令重排，必须加上volatile
    private volatile static LazyDemo lazyDemo;

    public static LazyDemo getInstance(){

        // 加锁
        if (lazyDemo == null) {
            synchronized (LazyDemo.class) {
                if (lazyDemo == null) {
                    lazyDemo = new LazyDemo();
                    // 不是一个原子性操作
                    // 可能会出现指令重排问题
                    /**
                     * 1. 分配内存空间
                     * 2. 执行构造方法，初始化对象
                     * 3. 把这个对象指向这个空间
                     */
                }
            }
        }
        return lazyDemo;
    }
}
