package com.wym.o2o.dao;

import com.wym.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    /*
        通过shop id 查询店铺商品类别
     */
    List<ProductCategory> queryProductCategoryList(long shopId);

    /*
        批量新增商品类别
     */
    int batcherInsertProductCategory(List<ProductCategory> productCategoryList);

    /*
        删除指定商品类别
     */
    int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId") long shopId);

}
