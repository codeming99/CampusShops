package com.wym.o2o.utils;

public class PageCalculator {
    //用来计算从多少行取数据
    public static int calculateRowIndex(int pageIndex,int pageSize){
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
