package com.kang.estimate.core.base.controller;

import com.kang.estimate.core.error.BussinessException;
import com.kang.estimate.core.error.EmBussinessError;
import com.kang.estimate.core.response.CommonReturnType;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    @ExceptionHandler(Exception.class)//指定处理的错误
    @ResponseBody
    public Object handlerException(HttpServletRequest request,Exception ex){
        Map<String,Object> responseData=new HashMap<>();
        ex.printStackTrace();
        if(ex instanceof BussinessException) {
            BussinessException bussinessException = (BussinessException) ex;
            responseData.put("errCode", bussinessException.getErrCode());
            responseData.put("errMsg", bussinessException.getErrMsg());
        }else if(ex instanceof IncorrectCredentialsException){
            responseData.put("errCode", EmBussinessError.WROING_PASSWORD.getErrCode());
            responseData.put("errMsg",EmBussinessError.WROING_PASSWORD.getErrMsg());
        }else{
            responseData.put("errCode", EmBussinessError.UNKNOW_ERROR.getErrCode());
            responseData.put("errMsg",EmBussinessError.UNKNOW_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData,"fail");
    }
}
