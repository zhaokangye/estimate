package com.kang.estimate.module.MonitorStats.controller;

import com.jcraft.jsch.JSchException;
import com.kang.estimate.controller.ViewModel.UserVO;
import com.kang.estimate.entity.MonitorStats;
import com.kang.estimate.core.error.BussinessException;
import com.kang.estimate.core.error.EmBussinessError;
import com.kang.estimate.core.response.CommonReturnType;
import com.kang.estimate.module.MonitorStats.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class MonitorController {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    MonitorService monitorService;

    @RequestMapping("/monitorServer/{host}/{code}/{startTime}/{endTime}")
    public CommonReturnType monitorServer(@PathVariable("host") String host,
                                          @PathVariable("code") String code,
                                          @PathVariable("startTime") String startTime,
                                          @PathVariable("endTime") String endTime) throws BussinessException, InterruptedException, JSchException, IOException {
        UserVO userVO=(UserVO)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if(userVO==null){
            throw new BussinessException(EmBussinessError.NOT_LOGIN);
        }
        ArrayList<MonitorStats> MonitorStatsList= monitorService.requestCPU(code,userVO.getUserid(),host,startTime,endTime);
        return CommonReturnType.create(MonitorStatsList);
    }

}
