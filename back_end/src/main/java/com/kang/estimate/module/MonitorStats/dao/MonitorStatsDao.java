package com.kang.estimate.module.MonitorStats.dao;

import com.kang.estimate.entity.MonitorStats;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MonitorStatsDao {

    @Insert("insert into monitorStats(hostURL,codeName,time,userUsage,niceUsage,systemUsage,iowaitUsage,stealUsage,idleUsage) " +
            "values(#{hostURL},#{codeName},#{time},#{userUsage},#{niceUsage},#{systemUsage},#{iowaitUsage},#{stealUsage},#{idleUsage})")
    void insert(MonitorStats monitorStats);
}
