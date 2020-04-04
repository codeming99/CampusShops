package com.wym.o2o.dao;

import com.wym.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    /*
        插入商品
     */
    int insertProdcut(Product product);

    /**
     * 通过productId查询唯一的商品信息
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /**
     * 更新商品信息
     * @param product
     * @return
     */
    int updateProduct(Product product);


    /**
     * 商品列表查询，可输入的条件有:商品名称（模糊），商品状态，店铺Id，商品类别
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition")Product productCondition, @Param("rowIndex")int rowIndex,
                                   @Param("pageSize")int pageSize);

    /**
     * 查询商品总数
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition")Product productCondition);


    /**
     * 删除商品类别之前，将商品分类Id置为空
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);


}
