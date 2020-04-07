package com.wym.o2o.service;

import com.wym.o2o.dto.LocalAuthExecution;
import com.wym.o2o.entity.LocalAuth;
import com.wym.o2o.exceptions.LocalAuthOperationException;

public interface LocalAuthService {

    /**
     * 通过用户名和密码获取平台账号信息
     * @param username
     * @param password
     * @return
     */
    LocalAuth getLocalAuthByUsernameAndPwd(String username,String password);


    /**
     * 通过userId获取账号信息
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);


    /**
     * 生成平台专属账号
     * @param localAuth
     * @return
     * @throws LocalAuthOperationException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth)throws LocalAuthOperationException;


    /**
     * 修改账号密码
     * @param userId
     * @param username
     * @param password
     * @param newPassword
     * @return
     */
    LocalAuthExecution modifyLocalAuth(Long userId,String username,String password,String newPassword)
    throws LocalAuthOperationException;

}
