package com.hannes.callable;

import java.util.concurrent.Callable;

public class MyThread implements Callable<Integer> {
    @Override
    public Integer call() {
        System.out.println("call()");
        return 1024;
    }
}
