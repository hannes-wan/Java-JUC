package com.hannes.singleDemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

// enum 本身也是一个类class类
public enum EnumDemo {
    INSTANCE;
    public EnumDemo getInstance() {
        return INSTANCE;
    }
}

class Test {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        EnumDemo instance1 = EnumDemo.INSTANCE;

        // 尝试用反射破解
        // 证明不能用反射来破坏单例
        Constructor<EnumDemo> declareConstructor = EnumDemo.class.getDeclaredConstructor(String.class, int.class);
        declareConstructor.setAccessible(true);

        EnumDemo instance2 = declareConstructor.newInstance();

        System.out.println(instance1);
        System.out.println(instance2);
    }
}
