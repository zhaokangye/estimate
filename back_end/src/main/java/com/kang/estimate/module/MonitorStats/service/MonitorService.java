package com.kang.estimate.module.MonitorStats.service;

import com.jcraft.jsch.JSchException;
import com.kang.estimate.module.MonitorStats.dao.MonitorStatsDao;
import com.kang.estimate.entity.MonitorStats;
import com.kang.estimate.core.error.BussinessException;
import com.kang.estimate.module.DeployApplication.service.DeployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class MonitorService {

    @Autowired
    DeployService deployService;
    @Autowired
    MonitorStatsDao monitorStatsDao;

    public ArrayList<MonitorStats> requestCPU(String code,Integer userid,String host,String startTime,String endTime) throws InterruptedException, BussinessException, JSchException, IOException {
        String commad="sar -s "+startTime+" -e "+endTime;
        StringBuilder strResult=deployService.execCommad(userid,host,commad);
        //分析字符串
        String str=strResult.toString();
        String[] s0=str.split("\n");
        ArrayList<MonitorStats> MonitorStatsList=new ArrayList<>();
        for(int i=3;i<s0.length-1;i++){
            String[] strings=str.split("\n")[i].split(" ");
            ArrayList<String> arrayList=new ArrayList<String>();
            for(int j=0;j<strings.length;j++){
                if(!strings[j].isEmpty()){
                    arrayList.add(strings[j]);
                }
            }
            MonitorStats monitorStats=new MonitorStats();
            monitorStats.setCodeName(code);
            monitorStats.setHostURL(host);
            monitorStats.setTime(arrayList.get(0)+arrayList.get(1));
            monitorStats.setUserUsage(arrayList.get(3));
            monitorStats.setNiceUsage(arrayList.get(4));
            monitorStats.setSystemUsage(arrayList.get(5));
            monitorStats.setIowaitUsage(arrayList.get(6));
            monitorStats.setStealUsage(arrayList.get(7));
            monitorStats.setIdleUsage(arrayList.get(8));
            monitorStatsDao.insert(monitorStats);
            MonitorStatsList.add(monitorStats);
        }
        return MonitorStatsList;
    }

}
