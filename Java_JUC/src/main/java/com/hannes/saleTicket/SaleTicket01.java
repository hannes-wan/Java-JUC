package com.hannes.saleTicket;

public class SaleTicket01 {
    public static void main(String[] args) {

        // 并发：多线程操作同一个资源类，把资源丢入线程就行
        Ticket ticket = new Ticket();

        new Thread(()->{
            for (int i = 0; i < 60; i++) {
                ticket.sale();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 60; i++) {
                ticket.sale();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 60; i++) {
                ticket.sale();
            }
        },"C").start();
    }
}
