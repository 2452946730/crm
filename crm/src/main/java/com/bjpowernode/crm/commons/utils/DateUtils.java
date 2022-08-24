package com.bjpowernode.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理日期格式
 * @Date 2022/8/24 11:52
 */
public class DateUtils {
    /**
     * 处理日期的格式 : yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formateDateTime(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    /**
     * 处理日期的格式 : yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formateDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    /**
     * 处理日期的格式 : HH:mm:ss
     * @param date
     * @return
     */
    public static String formateTime(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
