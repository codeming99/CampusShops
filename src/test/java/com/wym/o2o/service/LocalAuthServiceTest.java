package com.wym.o2o.service;

import com.wym.o2o.BaseTest;
import com.wym.o2o.dto.LocalAuthExecution;
import com.wym.o2o.entity.LocalAuth;
import com.wym.o2o.entity.PersonInfo;
import com.wym.o2o.enums.WechatAuthStateEnum;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthServiceTest extends BaseTest {
    @Autowired
    private LocalAuthService localAuthService;

    @Test
    @Ignore
    public void testABindLocalAuth(){
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        String username = "testusername";
        String password = "testpassword";
        personInfo.setUserId(1L);
        localAuth.setPersonInfo(personInfo);
        localAuth.setUsername(username);
        localAuth.setPassword(password);

        LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
        assertEquals(WechatAuthStateEnum.SUCCESS.getState(),le.getState());

        localAuth = localAuthService.getLocalAuthByUserId(personInfo.getUserId());

        System.out.println("用户昵称:"+localAuth.getPersonInfo().getName());
        System.out.println("平台账号密码:"+localAuth.getPassword());
    }


    @Test
    public void testBModifyLocalAuth(){
        long userId = 1L;
        String username = "testusername";
        String password = "testpassword";
        String newPassword = "testnewpassword";

        LocalAuthExecution le = localAuthService.modifyLocalAuth(userId,username,password,newPassword);
        assertEquals(WechatAuthStateEnum.SUCCESS.getState(),le.getState());


    }
}
