package com.kang.estimate.module.PressureTest.service;

import com.kang.estimate.utils.PressureTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PressureTestService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //暂时只支持get方法
    public Map<String,Object> startPresssureTest(String key,String url,int threadNums) throws InterruptedException {
        Map<String,Object> map=new HashMap<String, Object>();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        String startTime = formatter.format(new Date());
        CountDownLatch begin=new CountDownLatch(1);
        CountDownLatch end=new CountDownLatch(threadNums);
        ExecutorService executorService= Executors.newFixedThreadPool(threadNums);
        //建立线程，准备并发
        for(int i=0;i<threadNums;i++){
            PressureTest pressureTest=new PressureTest(stringRedisTemplate,key,i,begin,end,url);
            executorService.submit(pressureTest);
        }
        begin.countDown();  //全部线程开始并发访问指定url
        end.await();        //等待所有线程执行完毕
        String endTime =formatter.format(new Date());
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return map;
    }
}
