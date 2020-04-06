package com.wym.o2o.service;

import com.wym.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

     String SCLISTKEY = "shopcategorylist";

    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
