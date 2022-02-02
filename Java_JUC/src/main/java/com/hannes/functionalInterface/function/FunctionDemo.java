package com.hannes.functionalInterface.function;

import java.util.function.Function;

/**
 * Function 函数型接口
 *      传入参数T，返回R类型
 *
 * 只要是函数型接口，就能用Lambda表达式简化
 */
public class FunctionDemo {
    public static void main(String[] args) {
        // 一个匿名内部类
        // 传入String类型，返回String类型
//        Function function = new Function<String, String>() {
//            @Override
//            public String apply(String str) {
//                return str;
//            }
//        };

        // Lambda表达式写Function
        Function function = (str) -> {return str;};

        System.out.println(function.apply("hello"));
    }
}
