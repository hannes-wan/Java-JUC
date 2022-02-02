package com.hannes.functionalInterface.supplier;

import java.util.function.Supplier;

/**
 * 供给型接口 Supplier
 *      没有输入，只有返回值
 */
public class SupplierDemo {
    public static void main(String[] args) {
//        Supplier<String> supplier = new Supplier<>() {
//            @Override
//            public String get() {
//                return "hello";
//            }
//        };

        Supplier supplier = () -> {
            return "hello";
        };
        System.out.println(supplier.get());

    }
}
