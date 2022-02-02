package com.hannes.producerAndConsumer.JUCStyle;

// JUC 生产者消费者问题
public class Factory {
    public static void main(String[] args) {
        Data data = new Data();

        Runnable produce = ()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable consume = ()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(produce,"A").start();
        new Thread(consume,"B").start();
        new Thread(produce,"C").start();
        new Thread(consume,"D").start();
    }
}
