package com.wym.o2o.web.local;

import com.wym.o2o.dto.LocalAuthExecution;
import com.wym.o2o.entity.LocalAuth;
import com.wym.o2o.entity.PersonInfo;
import com.wym.o2o.enums.LocalAuthStateEnum;
import com.wym.o2o.service.LocalAuthService;
import com.wym.o2o.utils.CodeUtil;
import com.wym.o2o.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "local",method = {RequestMethod.GET,RequestMethod.POST})
public class LocalAuthController {
    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 将用户信息与平台信息绑定
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindlocalauth",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> bindLocalAuth(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //验证码校验
        if(!CodeUtil.checkCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }

        String userName = HttpServletRequestUtil.getString(request,"userName");
        String password = HttpServletRequestUtil.getString(request,"password");

        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");

        if(userName != null && password != null && user != null && user.getUserId() != null){
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUsername(userName);
            localAuth.setPassword(password);
            localAuth.setPersonInfo(user);

            LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
            if(le.getState() == LocalAuthStateEnum.SUCCESS.getState()){
                modelMap.put("success",true);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg",le.getStateInfo());
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","用户名和密码不能为空");
        }

        return modelMap;
    }


    @RequestMapping(value = "/changelocalpwd",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> changeLocalPwd(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }

        String userName = HttpServletRequestUtil.getString(request,"userName");
        String password = HttpServletRequestUtil.getString(request,"password");
        String newPassoword = HttpServletRequestUtil.getString(request,"newPassword");

        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");

        if(userName != null && password != null && newPassoword != null && user != null
            && user.getUserId() != null && !password.equals(newPassoword)){
            try{
                //查看原先账号，看看与输入的账号是否一致，不一致则认为是非法操作
                LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
                if(localAuth == null || !localAuth.getUsername().equals(userName)){
                    modelMap.put("success",false);
                    modelMap.put("errMsg","输入的账号非本次登录的账号");
                    return modelMap;
                }

                //修改平台账号密码
                LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(),userName,password,newPassoword);
                if(le.getState() == LocalAuthStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",le.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }

        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入密码");
        }

        return modelMap;
    }


    @RequestMapping(value = "/logincheck",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> logincheck(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();

        boolean needVerify = HttpServletRequestUtil.getBoolean(request,"needVerify");
        if(needVerify && !CodeUtil.checkCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }

        String userName = HttpServletRequestUtil.getString(request,"userName");
        String password = HttpServletRequestUtil.getString(request,"password");

        if(userName != null && password != null){
            LocalAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(userName,password);
            if(localAuth != null){
                modelMap.put("success",true);

                request.getSession().setAttribute("user",localAuth.getPersonInfo());
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","用户名或密码错误");
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","用户名或密码不能为空");
        }

        return modelMap;
    }


    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> logout(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //将用户session置为空
        request.getSession().setAttribute("user",null);
        modelMap.put("success",true);
        return modelMap;
    }
}
