package com.hannes.functionalInterface.consumer;

import java.util.function.Consumer;

/**
 * 消费型接口 Consumer
 *      只有输入，没有返回值
 */
public class ConsumerDemo {

    public static void main(String[] args) {
//        Consumer<String> consumer = new Consumer<>() {
//            @Override
//            public void accept(String str) {
//                System.out.println(str);
//            }
//        };

        Consumer consumer = (str) -> System.out.println(str);

        consumer.accept("hello");
    }

}
