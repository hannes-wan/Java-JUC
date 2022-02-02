package com.hannes.functionalInterface.predicate;

import java.util.function.Predicate;

/**
 * 断言型接口 Predicate
 *      传入一个参数，返回一个boolean
 */
public class PredicateDemo {
    public static void main(String[] args) {
        // 判断字符串是否为空
//        Predicate<String> predicate = new Predicate<String>() {
//            @Override
//            public boolean test(String str) {
//                return str.isEmpty();
//            }
//        };

        Predicate<String> predicate = (str) -> {return str.isEmpty();};
        System.out.println(predicate.test("hello"));
    }
}
