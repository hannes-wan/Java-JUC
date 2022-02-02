package com.hannes.singleDemo;

// 静态内部类实现单例模式
public class HolderDemo {

    private HolderDemo(){}

    public static HolderDemo getInstance() {
        return InnerClass.HOLDER_DEMO;
    }

    public static class InnerClass {
        private static final HolderDemo HOLDER_DEMO = new HolderDemo();
    }
}
