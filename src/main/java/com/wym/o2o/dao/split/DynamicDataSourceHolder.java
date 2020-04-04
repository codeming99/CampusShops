package com.wym.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceHolder {
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static final String DB_MASTER = "master";
    public static final String DB_SLAVE = "slave";

    public static String getDbType() {
        String db = contextHolder.get();
        if(db == null){
            return DB_MASTER;
        }
        return DB_SLAVE;
    }

    //设置线程的dbType
    public static void setDbType(String str){
        logger.debug("所使用的数据源为:"+ str);
        contextHolder.set(str);
    }

    //清理连接类型
    public static void clearDBtype(){
        contextHolder.remove();
    }
}
