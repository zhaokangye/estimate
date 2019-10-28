package com.kang.estimate.module.PressureTest.controller;

import com.kang.estimate.core.response.CommonReturnType;
import com.kang.estimate.module.PressureTest.service.PressureTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class PressureTestController {

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    PressureTestService pressureTestService;

    @RequestMapping("/startPressureTest/{key}/{url}/{threadNums}")
    public CommonReturnType startPressureTest(@PathVariable("key") String key,
                                              @PathVariable("url") String url,
                                              @PathVariable("threadNums") int threadNums) throws InterruptedException {
        String targetURL="http://"+url;
        Map<String,Object> map=pressureTestService.startPresssureTest(key,targetURL,threadNums);
        return CommonReturnType.create(map);
    }
}
