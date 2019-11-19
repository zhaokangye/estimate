package com.kang.estimate.util;

import com.jcraft.jsch.SftpProgressMonitor;
import com.kang.estimate.core.redis.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MyProgressMonitor implements SftpProgressMonitor {

    private RedisUtil redisUtil=new RedisUtil(new RedisTemplate<>());

    private String key;

    private long totalSize;

    private long transfered;

    public MyProgressMonitor(String key,long totalSize){
        this.key=key;
        this.totalSize=totalSize;
    }

    @Override
    public void init(int op, String src, String dest, long max) {
        System.out.println("开始传输.");
    }
    /**
     * 当每次传输了一个数据块后，调用count方法，count方法的参数为这一次传输的数据块大小
     */
    @Override
    public boolean count(long count) {
        transfered=transfered+count;
        BigDecimal transferedBD=new BigDecimal(transfered);
        BigDecimal totalSizeBD=new BigDecimal(totalSize);
        BigDecimal percentage=transferedBD.divide(totalSizeBD,2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        redisUtil.set(key,percentage);
        System.out.println(key+"已传输"+percentage+"%");
        return true;
    }
    @Override
    public void end() {
        System.out.println("结束传输.");
    }
}
