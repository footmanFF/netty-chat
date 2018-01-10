package com.footmanff.chat;

/**
 * @author zhangli on 2018/1/8.
 */
public class Client2 {

    public static void main(String[] args) throws Exception {
        final Test test = new Test();
        try {
//            test.connect(8085, "广播");
            test.testLengthFieldHandler();
        } finally {
//            test.shutdown();
        }
    }

}
