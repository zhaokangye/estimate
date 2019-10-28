package com.kang.estimate.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Http {

    public Integer doGet(int index,String url){
        CloseableHttpClient client= HttpClientBuilder.create().build();
        CloseableHttpResponse response=null;
        try {
            HttpGet request=new HttpGet(url);
            response=client.execute(request);
            int statusCode=response.getStatusLine().getStatusCode();
            System.out.println(index+"号线程执行完成，状态为"+statusCode);
            return statusCode;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
                response.close();
            } catch (IOException e) {
                System.out.println(index+":"+e.getMessage());
            }
        }
        return null;
    }
}
