package com.wym.o2o.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    //从request信息中提取key，转化成整形
    public static int getInt(HttpServletRequest request,String key){
        try{
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    //转化成长整型
    public static long getLong(HttpServletRequest request,String key){
        try{
            return Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    public static Double getDouble(HttpServletRequest request,String key){
        try{
            return Double.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1d;
        }
    }


    public static boolean getBoolean(HttpServletRequest request,String key){
        try{
            return Boolean.valueOf(request.getParameter(key));
        }catch (Exception e){
            return false;
        }
    }

    public static String getString(HttpServletRequest request,String key){
        try{
            String result = request.getParameter(key);
            if(request != null){
                result = result.trim();
            }
            if("".equals(result)){
                result = null;
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }
}
