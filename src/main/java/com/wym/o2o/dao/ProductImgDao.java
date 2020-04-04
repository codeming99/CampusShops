package com.wym.o2o.dao;

import com.wym.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    /*
        批量插入商品图片
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品的所有详情图
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

    /**
     * 根据商品id删除详情图列表
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(Long productId);
}
