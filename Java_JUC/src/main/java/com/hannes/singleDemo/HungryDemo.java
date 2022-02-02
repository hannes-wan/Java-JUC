package com.hannes.singleDemo;

// 饿汉式单例
public class HungryDemo {
    private HungryDemo() {}

    private final static HungryDemo HUNGRY_DEMO = new HungryDemo();

    public static HungryDemo getInstance(){
        return HUNGRY_DEMO;
    }
}
