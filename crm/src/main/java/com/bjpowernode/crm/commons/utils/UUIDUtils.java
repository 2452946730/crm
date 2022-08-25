package com.bjpowernode.crm.commons.utils;

import java.util.UUID;

/**
 * @Date 2022/8/25 16:38
 */
public class UUIDUtils {
    public static String  getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
