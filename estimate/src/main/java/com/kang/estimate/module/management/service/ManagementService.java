package com.kang.estimate.module.management.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.kang.estimate.core.shiro.ShiroKit;
import com.kang.estimate.module.management.dao.ServerMapper;
import com.kang.estimate.module.management.dao.UserMapper;
import com.kang.estimate.module.management.entity.Server;
import com.kang.estimate.module.management.entity.User;
import com.kang.estimate.util.Ftp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 叶兆康
 */
@Service
public class ManagementService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ServerMapper serverMapper;
    @Resource
    private ShiroKit shiroKit;

    public boolean addUser(String userName,String password){
        User user=new User();
        user.setUserName(userName);
        user.setPassword(password);
        userMapper.insert(user);
        return true;
    }

    public boolean addServer(Server server){
        Integer userId=shiroKit.getId();
        server.setCreateBy(userId);
        server.setCreateTime(new Date());
        server.setId(null);
        server.setStsCd(null);
        serverMapper.insert(server);
        return true;
    }

    public boolean deleteServer(Integer serverId){
        serverMapper.deleteById(serverId);
        return true;
    }

    public Server serverDetail(Integer serverId){
        Server server=serverMapper.selectById(serverId);
        server.setPassword(null);
        return server;
    }

    public boolean editServer(Server server){
        server.setUpdateBy(shiroKit.getId());
        server.setUpdateTime(new Date());
        serverMapper.updateById(server);
        return true;
    }

    public List<Server> selectServerList(){
        Integer userId=shiroKit.getId();
        return serverMapper.selectServerListByUserId(userId);
    }

    public Page<Server> selectServerList(Page<Server> page){
        return page.setRecords(serverMapper.selectServerList(page,shiroKit.getId()));
    }

    public Boolean testConnection(Integer serverId) throws Exception {
        Server server=serverMapper.selectById(serverId);
        return Ftp.getFtpUtil(server).isSession();
    }
}
