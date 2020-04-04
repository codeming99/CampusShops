package com.wym.o2o.service;

import com.wym.o2o.BaseTest;
import com.wym.o2o.dto.ImageHolder;
import com.wym.o2o.dto.ProductExecution;
import com.wym.o2o.entity.Product;
import com.wym.o2o.entity.ProductCategory;
import com.wym.o2o.entity.Shop;
import com.wym.o2o.enums.ProductStateEnum;
import com.wym.o2o.exceptions.ProductOperationException;
import com.wym.o2o.exceptions.ShopOperationException;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    @Ignore
    public void testAaddProduct() throws ShopOperationException, FileNotFoundException, ProductOperationException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        //创建缩略图文件流
        File thumbnailFile = new File("/Users/小黄人.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(is,thumbnailFile.getName());
        //创建两个商品详情图文件流并将他们添加到详情图列表中
        File productImg1 = new File("/Users/小黄人.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("/Users/小黄人.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<>();
        productImgList.add(new ImageHolder(is1,productImg1.getName()));
        productImgList.add(new ImageHolder(is2,productImg2.getName()));
        //添加商品并验证
        ProductExecution pe = productService.addProduct(product,thumbnail,productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState() , pe.getState());
    }

    @Test
    public void testBModifyProduct() throws ProductOperationException,FileNotFoundException{
        Product product = new Product();
        Shop shop = new Shop();
        ProductCategory pc = new ProductCategory();
        shop.setShopId(1L);
        pc.setProductCategoryId(1L);
        product.setProductCategory(pc);
        product.setShop(shop);
        product.setProductId(1L);
        product.setProductName("正式的商品");
        product.setProductDesc("正式的商品");
        File thumbnailFile = new File("/Users/小黄人.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(is,thumbnailFile.getName());
        File productImg1 = new File("/Users/小黄人.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("/Users/小黄人.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<>();
        productImgList.add(new ImageHolder(is1,productImg1.getName()));
        productImgList.add(new ImageHolder(is2,productImg2.getName()));
        ProductExecution pe = productService.modifyProduct(product,thumbnail,productImgList);
        assertEquals(pe.getState(),ProductStateEnum.SUCCESS.getState());
    }
}
