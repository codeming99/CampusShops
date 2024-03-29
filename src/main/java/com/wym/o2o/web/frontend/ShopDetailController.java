package com.wym.o2o.web.frontend;


import com.wym.o2o.dto.ProductExecution;
import com.wym.o2o.entity.Product;
import com.wym.o2o.entity.ProductCategory;
import com.wym.o2o.entity.Shop;
import com.wym.o2o.service.ProductCategoryService;
import com.wym.o2o.service.ProductService;
import com.wym.o2o.service.ShopService;
import com.wym.o2o.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;


    /**
     * 获取店铺信息以及该店铺下的商品类别列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShopDetailPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        Shop shop;
        List<ProductCategory> productCategoryList;
        if(shopId != -1){
            shop = shopService.getByShopId(shopId);
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("shop",shop);
            modelMap.put("productCategoryList",productCategoryList);
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
            return modelMap;
        }
        return modelMap;
    }


    /**
     * 获取店铺下的商品列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listproductsbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listProductByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");

        if((pageIndex > -1) && (pageSize > -1) && (shopId > -1)){
            long productCategoryId = HttpServletRequestUtil.getLong(request,"productCategoryId");

            String productName = HttpServletRequestUtil.getString(request,"productName");

            Product productCondition = compactProductCondition4Search(shopId,productCategoryId,productName);

            ProductExecution pe = productService.getProductList(productCondition,pageIndex,pageSize);
            modelMap.put("productList",pe.getProductList());
            modelMap.put("count",pe.getCount());
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty pageIndex or pageSize");
            return modelMap;
        }
        return modelMap;
    }

    private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);

        if(productCategoryId != -1L){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }

        if(productName != null){
            productCondition.setProductName(productName);
        }

        productCondition.setEnableStatus(1);
        return productCondition;
    }

}
