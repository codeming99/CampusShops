package com.wym.o2o.dao;

import com.wym.o2o.BaseTest;
import com.wym.o2o.entity.Product;
import com.wym.o2o.entity.ProductCategory;
import com.wym.o2o.entity.ProductImg;
import com.wym.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAInsetProduct(){
        Shop shop1 = new Shop();
        shop1.setShopId(1L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(1L);
        Product product1 = new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试1描述");
        product1.setImgAddr("test1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setProductCategory(pc1);
        product1.setShop(shop1);
        Product product2 = new Product();
        product2.setProductName("测试2");
        product2.setProductDesc("测试2描述");
        product2.setImgAddr("test2");
        product2.setPriority(2);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setProductCategory(pc1);
        product2.setShop(shop1);
        Product product3 = new Product();
        product3.setProductName("测试3");
        product3.setProductDesc("测试3描述");
        product3.setImgAddr("test3");
        product3.setPriority(3);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setProductCategory(pc1);
        product3.setShop(shop1);
        int effectedNum = productDao.insertProdcut(product1);
        assertEquals(1,effectedNum);
        effectedNum = productDao.insertProdcut(product2);
        assertEquals(1,effectedNum);
        effectedNum = productDao.insertProdcut(product3);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testBqueryProductList(){
        Product productCondition = new Product();
        List<Product> productList = productDao.queryProductList(productCondition,0,3);
        assertEquals(3,productList.size());
        int count = productDao.queryProductCount(productCondition);
        assertEquals(15,count);
        productCondition.setProductName("测试");
        productList = productDao.queryProductList(productCondition,0,3);
        assertEquals(3,productList.size());
        count = productDao.queryProductCount(productCondition);
        assertEquals(3,count);
    }

    @Test
    public void testCQueryProductByProductId(){
        long productId = 1;
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);
        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2,effectedNum);
        Product product = productDao.queryProductById(productId);
        assertEquals(2,product.getProductImgList().size());
        effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2,effectedNum);
    }

    @Test
    public void testDUpdateProduct(){
        Product product = new Product();
        ProductCategory productCategory = new ProductCategory();
        Shop shop = new Shop();
        productCategory.setProductCategoryId(1L);
        shop.setShopId(1L);
        product.setProductId(1L);
        product.setProductCategory(productCategory);
        product.setShop(shop);
        product.setProductName("第二个产品");
        int effectedNum = productDao.updateProduct(product);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testEUpdateProductCategoryToNull(){
        int effectedNum = productDao.updateProductCategoryToNull(2L);
        assertEquals(0,effectedNum);
    }

}
