package com.wym.o2o.utils;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    // 需要加密的字符串数组
    private String[] encryptPropNames = {"jdbc.username","jdbc.password"};

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if(isEncryptProp(propertyName)){
            // 对已加密的字段进行解密工作
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        }else{
            return propertyValue;
        }
    }

    private boolean isEncryptProp(String propertyName) {
        for(String encryptpropertyName : encryptPropNames){
            if(encryptpropertyName.equals(propertyName)){
                return true;
            }
        }

        return false;
    }


}
