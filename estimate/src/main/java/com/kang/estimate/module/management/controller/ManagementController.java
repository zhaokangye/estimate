package com.kang.estimate.module.management.controller;

import com.kang.estimate.core.base.BaseController;
import com.kang.estimate.core.response.CommonReturnType;
import com.kang.estimate.module.management.service.ManagementService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ManagementController extends BaseController {

    @Autowired
    private ManagementService managementService;

    /**
     * 注册
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/user")
    public CommonReturnType user(@RequestParam String userName, @RequestParam String password){
        return CommonReturnType.create(managementService.addUser(userName,password));
    }

    /**
     * 登陆
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/login")
    public CommonReturnType login(@RequestParam String userName,@RequestParam String password){
        UsernamePasswordToken token=new UsernamePasswordToken(userName,password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return CommonReturnType.create(null);
    }

    @PostMapping("/server")
    public CommonReturnType addServer(){

    }
}
