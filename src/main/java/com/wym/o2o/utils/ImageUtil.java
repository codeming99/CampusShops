package com.wym.o2o.utils;

import com.wym.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public  class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static String basePath1;

    //处理获取路径中的空格问题(%20)
    static {
        try {
            basePath1 = URLDecoder.decode(basePath,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private static final SimpleDateFormat sDareFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static File transferCommonsMultipartFiletoFile(CommonsMultipartFile cFile){
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /*
            处理缩略图，并返回新生成图片的相对值路径
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        //随机文件名
        String realFileName = getRandomFileName();
        //文件扩展名
        String extension = getFileExtension(thumbnail.getImageName());
        //创建文件夹
        makeDirPath(targetAddr);
        //文件相对地址名
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is:" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath1 + "watermark.jpg")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        }catch (IOException e){
            logger.error(e.toString());
            e.printStackTrace();
        }

        return relativeAddr;
    }

    /*
        处理详情图，并返回新生成图片的相对值路径
     */
    public static String generateNormalThumbnail(ImageHolder thumbnail, String targetAddr) {
        //随机文件名
        String realFileName = getRandomFileName();
        //文件扩展名
        String extension = getFileExtension(thumbnail.getImageName());
        //创建文件夹
        makeDirPath(targetAddr);
        //文件相对地址名
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is:" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath1 + "watermark.jpg")),0.25f)
                    .outputQuality(0.9f).toFile(dest);
        }catch (IOException e){
            logger.error(e.toString());
            e.printStackTrace();
        }

        return relativeAddr;
    }


    /*
            创建目标路径所涉及到的目录，即F:/projectdev/image/xxx.jpg
            那么projectdev image这些文件夹都会自动创建出来
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /*
            获取输入文件流的扩展名
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /*
            生成随机文件名，当前年月日小时分钟秒钟+五位随机数
     */
    public static String getRandomFileName() {
        //获取随机的五位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDareFormat.format(new Date());
        return  nowTimeStr + rannum;
    }

    public static void main(String[] args) throws IOException {
        //从当前线程获得类加载器再得到资源路径
        Thumbnails.of(new File("C:\\Users\\Wym\\Desktop\\图片\\小黄人.jpg"))
                .size(200,200).watermark(Positions.BOTTOM_RIGHT,
                ImageIO.read(new File(basePath + "/watermark.jpg")),0.25f).outputQuality(0.8f)
                .toFile("C:\\Users\\Wym\\Desktop\\图片\\小黄人3.jpg");
        //System.out.println(basePath1);
    }

    /*
        stroePath是文件的路径还是目录的路径
        如果是文件的路径则删除该文件
        如果是目录的路径则删除该目录下的所有文件
     */
    public static void deleteFileOrPath(String stroePath){
        File fileOrPath = new File(PathUtil.getImgBasePath() + stroePath);
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File files[] = fileOrPath.listFiles();
                for(int i = 0;i < files.length;i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
