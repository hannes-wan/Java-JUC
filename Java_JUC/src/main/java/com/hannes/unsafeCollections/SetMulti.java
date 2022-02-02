package com.hannes.unsafeCollections;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SetMulti {
    public static void main(String[] args) {
        // 证明了并发下Set的不安全性
        // HashSet并没有存储Hash键，只存储value，但是Hash值存在
        Set<String> set = new HashSet<>();

        /**
         * 解决方案：
         *      (1) Collections.synchronizedSet(new HashSet<>())
         *      Set<String> set = Collections.synchronizedSet(new HashSet<>());
         *      (2) CopyOnWriteArraySet<>()
         *      Set<String> set = new CopyOnWriteArraySet<>();
         */

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
