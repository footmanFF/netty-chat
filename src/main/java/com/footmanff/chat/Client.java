package com.footmanff.chat;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhangli on 2018/1/8.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        final Test test = new Test();

        final CountDownLatch latch = new CountDownLatch(2);

        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        test.connect(8081, "channel1");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            }).start();

            new Thread(new Runnable() {
                public void run() {
                    try {
                        test.connect(8082, "channel2");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            }).start();
            
            latch.wait();
        } finally {
            test.shutdown();
        }
    }

}
