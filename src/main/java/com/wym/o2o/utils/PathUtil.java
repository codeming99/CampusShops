package com.wym.o2o.utils;

public class PathUtil {
    //依据操作系统的不同得到相应的分隔符
    private static String separator = System.getProperty("file.separator");
    //获取图片的基本地址
    public static String getImgBasePath(){

        //根据操作系统的不同得出不同的路径表达
        String os = System.getProperty("os.name");
        String basePath;
        if(os.toLowerCase().startsWith("win")){
            //win系统
            basePath = "F:/projectdev/image/";
        }else{
            //macos系统
            basePath = "/Users/wym/image/";
        }
        basePath = basePath.replace("/",separator);
        return basePath;
    }

    //获得图片的相对地址
    public static String getShopImagePath(long shopId){
        String imagePath = "upload/item/shop/" + shopId + "/";
        return imagePath.replace("/",separator);
    }

}
