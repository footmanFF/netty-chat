package com.footmanff.chat.util;

import java.util.UUID;

/**
 * @author zhangli on 10/01/2018.
 */
public class IdUtil {
    
    public static String id(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
}
