package com.wym.o2o.service;

import com.wym.o2o.dto.ProductCategoryExecution;
import com.wym.o2o.entity.ProductCategory;
import com.wym.o2o.exceptions.ProductCategoryOperationEeception;

import java.util.List;

public interface ProductCategoryService {
    /*
        查询指定某个店铺下的所有商品类别信息
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    /**
     * 批量增加商品类别
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationEeception
     */
    ProductCategoryExecution batchAddProdcutCategory(List<ProductCategory> productCategoryList)
        throws ProductCategoryOperationEeception;

    /**
     * 将此类别下的商品里的类别id置为空，再删除该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationEeception
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)
        throws ProductCategoryOperationEeception;
}
