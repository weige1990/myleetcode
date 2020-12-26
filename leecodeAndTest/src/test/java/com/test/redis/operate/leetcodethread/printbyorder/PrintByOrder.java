package com.test.redis.operate.leetcodethread.printbyorder;

import org.testng.annotations.Test;

public class PrintByOrder {

    @Test
    public void testPrintByOrder() {
        //1三个线程
        final Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("A");

            }
        }, "A");
        final  Thread b = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    a.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
            }
        }, "B");

        final Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    b.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("C");
            }
        }, "C");


        final Thread d = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("D");
            }
        }, "D");
        //2 按顺序执行三个线程
        a.start();
        b.start();
        c.start();
        d.start();
    }

}
