package com.wwj.util;

/**
 * 整体的工具类
 */
public class Utils {

    /**
     * 判断给定的字符串是否为空，包括null和空串
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }
}
