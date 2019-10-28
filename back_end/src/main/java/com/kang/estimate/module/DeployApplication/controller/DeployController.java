package com.kang.estimate.module.DeployApplication.controller;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.kang.estimate.controller.ViewModel.UserVO;
import com.kang.estimate.core.base.BaseController;
import com.kang.estimate.core.error.BussinessException;
import com.kang.estimate.core.error.EmBussinessError;
import com.kang.estimate.core.response.CommonReturnType;
import com.kang.estimate.module.DeployApplication.service.DeployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class DeployController extends BaseController {

    @Autowired
    DeployService deployService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("/execCommad/{host}/")
    public CommonReturnType execCommad(@PathVariable("host") String host,
                                       String commad)
            throws InterruptedException, JSchException, IOException, BussinessException {
        UserVO userVO=(UserVO)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if(userVO==null){
            throw new BussinessException(EmBussinessError.NOT_LOGIN);
        }
        StringBuilder strResult=deployService.execCommad(userVO.getUserid(),host,commad);
        return CommonReturnType.create(strResult);
    }

    @RequestMapping("/deploy/{src}/{host}/{dst}")
    public CommonReturnType deployApplication(@PathVariable("src") String src,
                                              @PathVariable("host") String host,
                                              @PathVariable("dst") String dst)
            throws BussinessException, JSchException, SftpException {
        UserVO userVO=(UserVO)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if(userVO==null){
            throw new BussinessException(EmBussinessError.NOT_LOGIN);
        }
        deployService.deployApplication(userVO.getUserid(),src,host,dst);
        return CommonReturnType.create(null);
    }
}
