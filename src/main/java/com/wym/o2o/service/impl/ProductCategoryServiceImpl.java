package com.wym.o2o.service.impl;

import com.wym.o2o.dao.ProductCategoryDao;
import com.wym.o2o.dao.ProductDao;
import com.wym.o2o.dto.ProductCategoryExecution;
import com.wym.o2o.entity.ProductCategory;
import com.wym.o2o.enums.ProductCategoryStateEnum;
import com.wym.o2o.exceptions.ProductCategoryOperationEeception;
import com.wym.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    public ProductCategoryExecution batchAddProdcutCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationEeception {
        if(productCategoryList != null && productCategoryList.size() > 0){
            try{
                int effectNum = productCategoryDao.batcherInsertProductCategory(productCategoryList);
                if(effectNum <= 0){
                    throw new ProductCategoryOperationEeception("店铺类别创建失败");
                }else{
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationEeception("batchAddProdcutCategory error"+e.getMessage());
            }
        }else{
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }

    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationEeception {
        //TODO 将此商品类别下的商品的类别id置为空
        try{
            int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
            if(effectedNum <= 0){
                throw new ProductCategoryOperationEeception("商品类别删除失败");
            }
        }catch (Exception e){
            throw new ProductCategoryOperationEeception("deleteProductCategory error" + e.getMessage());
        }

        try{
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if(effectedNum <= 0){
                throw new ProductCategoryOperationEeception("商品类别删除失败");
            }else{
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new ProductCategoryOperationEeception("deleteProductCategory error" + e.getMessage());
        }

    }
}
