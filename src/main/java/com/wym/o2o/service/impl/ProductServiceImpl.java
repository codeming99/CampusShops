package com.wym.o2o.service.impl;

import com.wym.o2o.dao.ProductDao;
import com.wym.o2o.dao.ProductImgDao;
import com.wym.o2o.dto.ImageHolder;
import com.wym.o2o.dto.ProductExecution;
import com.wym.o2o.entity.Product;
import com.wym.o2o.entity.ProductImg;
import com.wym.o2o.enums.ProductStateEnum;
import com.wym.o2o.exceptions.ProductOperationException;
import com.wym.o2o.service.ProductService;
import com.wym.o2o.utils.ImageUtil;
import com.wym.o2o.utils.PageCalculator;
import com.wym.o2o.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        //1.处理缩略图，获取缩略图的相对路径并赋值给product
        //2.往tb_product写入商品信息，获取productId
        //3.结合productId批量处理商品详情图
        //4.将商品详情图列表批量插入tb_product_img中
        if(product != null && product.getShop() != null && product.getShop().getShopId() != null){
            //给商品设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为上架状态
            product.setEnableStatus(1);
            //若商品缩略图不为空则添加
            if(thumbnail != null){
                addThumbnail(product,thumbnail);
            }
            try{
                //创建商品信息
                int effectedNum = productDao.insertProdcut(product);
                if(effectedNum <= 0){
                    throw new ProductOperationException("创建商品失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("addProduct error:" + e.getMessage());
            }
            //若商品详情图不为空则添加
            if(productImgList != null && productImgList.size() > 0){
                addProductImgList(product,productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS,product);
        }else {
            //传参为空则返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPTY);
        }

    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    //1.若缩略图参数有值，则处理缩略图
    //若原先存在缩略图则先删除再添加新图，之后获取缩略图路径存入到product中
    //2.若商品详情图列表参数有值，对商品详情图列表进行同样的操作
    //3.将tb_product_img下面的该商品原先的详情图删掉
    //4.更新tb_product_img以及tb_product的信息
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {

        if(product != null && product.getShop() != null && product.getShop().getShopId() != null){
            product.setLastEditTime(new Date());
            if(thumbnail != null){
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if(tempProduct.getImgAddr() != null){
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product,thumbnail);
            }

            if(productImgHolderList != null && productImgHolderList.size() > 0){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgHolderList);
            }
            try{
                //更新商品信息
                int effectedNum = productDao.updateProduct(product);
                if(effectedNum <= 0){
                    throw new ProductOperationException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS,product);
            }catch (Exception e){
                throw new ProductOperationException("更新商品信息失败"+e.toString());
            }
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }


    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(productCondition,rowIndex,pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    /**
     * 删除某个商品下的所有详情图
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        //根据productId获取原来的图片列表
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        //逐一删除原来的详情图
        for(ProductImg productImg : productImgList){
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //删掉数据库的图片信息
        productImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 批量添加商品详情图到列表
     * @param product
     * @param productImgHolderList
     * @throws ProductOperationException
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        //获取图片存储的相对路径，这里直接存储到相应店铺(依据shopId)的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        //遍历商品详情图列表,并逐一添加到productImg实体类里
        for(ImageHolder productImgHolder : productImgHolderList){
            String imgAddr = ImageUtil.generateNormalThumbnail(productImgHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }

        //如果确实有图片添加
        if(productImgList.size() > 0){
            try{
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if(effectedNum <= 0){
                    throw new ProductOperationException("创建商品详情图失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品详情图失败"+e.toString());
            }
        }
    }

    /**
     * 添加缩略图
     * 获得要添加缩略图的相对路径并将路径存放到product里
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);
    }
}
