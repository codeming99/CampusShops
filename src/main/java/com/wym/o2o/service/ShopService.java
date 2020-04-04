package com.wym.o2o.service;

import com.wym.o2o.dto.ImageHolder;
import com.wym.o2o.dto.ShopExecution;
import com.wym.o2o.entity.Shop;
import com.wym.o2o.exceptions.ShopOperationException;

import java.io.InputStream;

public interface ShopService {
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /*
        通过店铺id获取店铺信息
     */
    Shop getByShopId(long shopId);

    /*
        更新店铺信息，包括对图片的处理
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /*
        根据shopCondition分页返回相应店铺列表
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
}
