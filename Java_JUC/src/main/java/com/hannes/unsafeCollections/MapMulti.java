package com.hannes.unsafeCollections;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MapMulti {
    public static void main(String[] args) {
        /**
         * HashMap的线程也是不安全的
         * HashMap在工作中不会直接new HashMap<>()
         * 需要加上加载因子和初始化容量
         * 默认加载因子0.75f
         * 默认初始化容量：16
         * 所以new HashMap() == new HashMap(16, 0.75)
         */
        Map<String, String> map = new HashMap<>();

        /**
         * 解决方案：
         *      (1) Hashtable
         *      Map<String, String> hashtable = new Hashtable<>();
         *      (2) Collections.synchronizedMap
         *      Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
         *      (3) ConcurrentHashMap
         *      Map<String, String> map = new ConcurrentHashMap<>();
         */

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName() ,UUID.randomUUID().toString().substring(0,5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }

    }
}
