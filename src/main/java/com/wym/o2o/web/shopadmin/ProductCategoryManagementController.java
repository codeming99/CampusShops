package com.wym.o2o.web.shopadmin;

import com.wym.o2o.dto.ProductCategoryExecution;
import com.wym.o2o.dto.Result;
import com.wym.o2o.entity.ProductCategory;
import com.wym.o2o.entity.Shop;
import com.wym.o2o.enums.ProductCategoryStateEnum;
import com.wym.o2o.exceptions.ProductCategoryOperationEeception;
import com.wym.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProducCategoryList(HttpServletRequest request){
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        List<ProductCategory> list;
        if(currentShop != null && currentShop.getShopId() > 0){
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<>(true, list);
        }else{
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<>(false,ps.getState(),ps.getStateInfo());
        }
    }

    @RequestMapping(value = "/addproductcategorys",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
                                                  HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        for(ProductCategory pc : productCategoryList){
            pc.setShopId(currentShop.getShopId());
        }
        if(productCategoryList != null && productCategoryList.size() > 0){
            try{
                ProductCategoryExecution pe = productCategoryService.batchAddProdcutCategory(productCategoryList);
                if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }
            }catch (ProductCategoryOperationEeception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请至少输入一个商品类别");
        }

        return modelMap;
    }

    @RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(productCategoryId != null && productCategoryId > 0){
            try{
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId,currentShop.getShopId());
                if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }
            }catch (ProductCategoryOperationEeception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请至少选择一个商品类别");
        }
        return modelMap;
    }

}
