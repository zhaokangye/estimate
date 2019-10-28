package com.kang.estimate.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class PressureTest implements Runnable {


    private String key;
    private int index;
    private String url;
    private CountDownLatch begin;
    private CountDownLatch end;
    private StringRedisTemplate stringRedisTemplate;



    public PressureTest(StringRedisTemplate stringRedisTemplate,String key,int index,CountDownLatch begin,CountDownLatch end,String url){
        this.key=key;
        this.index=index;
        this.url=url;
        this.begin=begin;
        this.end=end;
        this.stringRedisTemplate=stringRedisTemplate;
    }

    @Override
    public void run() {
        CloseableHttpClient client= HttpClientBuilder.create().build();
        CloseableHttpResponse response=null;
        try {
            begin.await();
            HttpGet request=new HttpGet(url);
            response=client.execute(request);
            Integer statusCode=response.getStatusLine().getStatusCode();
            System.out.println(index+"号线程执行完成，状态为"+statusCode);
            stringRedisTemplate.opsForValue().set("responseStatsList:code="+key+"&index="+index,statusCode.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
                response.close();
            } catch (IOException e) {
                System.out.println(index+"号线程错误信息："+e.getMessage());
            }
            end.countDown();
        }
    }
}
