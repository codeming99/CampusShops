package com.wym.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import com.wym.o2o.BaseTest;
import com.wym.o2o.dto.ImageHolder;
import com.wym.o2o.dto.ShopExecution;
import com.wym.o2o.entity.Area;
import com.wym.o2o.entity.PersonInfo;
import com.wym.o2o.entity.Shop;
import com.wym.o2o.entity.ShopCategory;
import com.wym.o2o.enums.ShopStateEnum;
import com.wym.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;

	@Test
	@Ignore
	public void testGetShopList(){
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition,8,2);
		System.out.println("店铺列表数:" + se.getShopList().size());
		System.out.println("店铺种数:" + se.getCount());
	}

	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException,FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(15L);
		shop.setShopName("修改了！");
		File shopImg = new File("/Users/小黄人.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder(is,shopImg.getName());
		ShopExecution se = shopService.modifyShop(shop,imageHolder);
		System.out.println("新的图片地址:" + se.getShop().getShopImg());
	}

	@Test
	public void testAddShop() throws ShopOperationException, FileNotFoundException {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(22L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺4");
		shop.setShopDesc("test4");
		shop.setShopAddr("test4");
		shop.setPhone("test4");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File shopImg = new File("/Users/小黄人.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder(is,shopImg.getName());
		ShopExecution se = shopService.addShop(shop, imageHolder);
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
	}

}
