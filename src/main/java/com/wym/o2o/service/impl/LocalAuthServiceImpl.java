package com.wym.o2o.service.impl;

import com.wym.o2o.dao.LocalAuthDao;
import com.wym.o2o.dto.LocalAuthExecution;
import com.wym.o2o.entity.LocalAuth;
import com.wym.o2o.enums.LocalAuthStateEnum;
import com.wym.o2o.exceptions.LocalAuthOperationException;
import com.wym.o2o.service.LocalAuthService;
import com.wym.o2o.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {
    @Autowired
    private LocalAuthDao localAuthDao;

    @Override
    public LocalAuth getLocalAuthByUsernameAndPwd(String username, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(username, MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Override
    @Transactional
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
        if(localAuth == null || localAuth.getPassword() == null || localAuth.getUsername() == null
        || localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getUserId() == null){
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }

        //查询此用户是否已绑定过平台账号
        LocalAuth tempLocal = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
        if(tempLocal != null){
            //绑定过则退出，保证账号唯一性
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
        }
        try{
            //之前没有绑定过账号，则创建一个平台账号
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            //对密码进行MD5加密
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            int effectedNum = localAuthDao.insertLocalAuth(localAuth);

            if(effectedNum <= 0){
                throw new LocalAuthOperationException("账号创建失败");
            }else {
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new LocalAuthOperationException("创建账号失败");
        }


    }

    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword)
            throws LocalAuthOperationException {
        // 非空判断，判断传入的用户Id,帐号,新旧密码是否为空，新旧密码是否相同，若不满足条件则返回错误信息
        if (userId != null && userName != null && password != null && newPassword != null
                && !password.equals(newPassword)) {
            try {
                // 更新密码，并对新密码进行MD5加密
                int effectedNum = localAuthDao.updateLocalAuth(userId, userName, MD5.getMd5(password),
                        MD5.getMd5(newPassword), new Date());
                // 判断更新是否成功
                if (effectedNum <= 0) {
                    throw new LocalAuthOperationException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new LocalAuthOperationException("更新密码失败:" + e.toString());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
