package com.hannes.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 题目要求：
 *      1分钟内完成，只用一行代码
 *      五个用户，筛选：
 *          1. ID为偶数
 *          2. 年龄大于23岁
 *          3. 用户名转为大写字母
 *          4. 用户名字母倒着排序
 *          5. 只输出一个用户
 */
public class StreamTest {
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>( Arrays.asList(
                new User(1, "a", 21),
                new User(2, "b", 22),
                new User(3, "c", 23),
                new User(4, "d", 24),
                new User(6, "e", 25)));

        // 计算使用Stream流
        // 链式编程、lambda表达式、函数式接口、Stream流式计算
        list.stream()
                .filter((u) -> {return u.getId()%2==0;})
                .filter((u) -> {return u.getAge()>23;})
                .map((u) -> {return u.getName().toUpperCase();})
                .sorted(Comparator.naturalOrder())
                .limit(1)
                .forEach(System.out::println);
    }
}
