package com.kang.estimate.module.management.service;

import com.kang.estimate.module.management.dao.UserMapper;
import com.kang.estimate.module.management.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {

    @Autowired
    private UserMapper userMapper;

    public boolean addUser(String userName,String password){
        User user=new User();
        user.setUserName(userName);
        user.setPassword(password);
        userMapper.insert(user);
        return true;
    }
}
