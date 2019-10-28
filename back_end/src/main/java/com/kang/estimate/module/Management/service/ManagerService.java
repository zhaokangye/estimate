package com.kang.estimate.module.Management.service;

import com.kang.estimate.controller.ViewModel.ServerVO;
import com.kang.estimate.module.Management.dao.ServerMapper;
import com.kang.estimate.module.Management.dao.UserMapper;
import com.kang.estimate.module.Management.entity.Server;
import com.kang.estimate.module.Management.entity.User;
import com.kang.estimate.core.error.BussinessException;
import com.kang.estimate.core.error.EmBussinessError;
import com.kang.estimate.utils.Convert;
import com.kang.estimate.utils.SSH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class ManagerService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ServerMapper serverMapper;

    static final int USERNAME_MAXLENGTH=8;
    static final int PASSWORD_MAXLENGTH=20;
    static final int HOST_MAXLENGTH=15;
    static final int SERVERNAME_MAXLENGTH=20;


    public User findUserByUserName(String username){
        return userMapper.findUser(username);
    }

    public Integer addUser(String username,String password) throws BussinessException {
        //检查用户名、密码长度
        User user=new User();
        if(findUserByUserName(username)!=null){
            throw new BussinessException(EmBussinessError.ALREADY_REGISTERD);
        }
        if(username.length()>USERNAME_MAXLENGTH || password.length()>PASSWORD_MAXLENGTH){
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR.setErrMsg("用户名为8位，密码为20位"));
        }
        user.setUsername(username);
        user.setPassword(password);
        return userMapper.insert(user);
    }

    public int deleteUser(Integer userid,String username,String password) throws BussinessException {
        User user=vaildUser(userid,username,password);
        serverMapper.deleteAllServer(userid);
        return userMapper.deleteUser(user);
    }

    public User login(String username,String password) throws BussinessException {
        User user= userMapper.findUser(username);
        if(user==null){
            throw new BussinessException(EmBussinessError.NOT_EXIST);
        }
        if(!password.equals(user.getPassword())){
            throw new BussinessException(EmBussinessError.WROING_PASSWORD);
        }
        return user;
    }

    public User vaildUser(Integer userid,String username,String password) throws BussinessException {
        User user=findUserByUserName(username);
        if (user==null){
            throw new BussinessException(EmBussinessError.NOT_EXIST);
        }
        if(!userid.equals(user.getUserid())){
            throw new BussinessException(EmBussinessError.NOT_EXIST);
        }
        if(!password.equals(user.getPassword())){
            throw new BussinessException(EmBussinessError.WROING_PASSWORD);
        }
        return user;
    }

    public Server addServer(String host,Integer port,String role,String password,String servername,Integer userid) throws BussinessException {
        if(serverMapper.findServer(host)!=null){
            throw new BussinessException(EmBussinessError.ALREADY_REGISTERD.setErrMsg("已添加该主机"));
        }
        if(host.length()>HOST_MAXLENGTH || password.length()>PASSWORD_MAXLENGTH ||port==null||role==null||servername.length()>SERVERNAME_MAXLENGTH){
           throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }
        Server server=new Server();
        server.setHost(host);
        server.setPort(port);
        server.setRole(role);
        server.setPassword(password);
        server.setServername(servername);
        server.setUserid(userid);
        serverMapper.addServer(server);
        return server;
    }
    public ArrayList<ServerVO> findServerByUserid(Integer userid){
        ArrayList<ServerVO> serverVOList=new ArrayList<ServerVO>();
        Iterator iterator= serverMapper.findServerByUserid(userid).iterator();
        while (iterator.hasNext()){
            serverVOList.add(Convert.convertFromServer((Server) iterator.next()));
        }
        return serverVOList;
    }

    public Integer deleteServer(Integer userid,String host) throws BussinessException {
        Server server= serverMapper.findServer(host);
        if(server==null){
            throw new BussinessException(EmBussinessError.NOT_EXIST);
        }
        if(!userid.equals(server.getUserid())){
            throw new BussinessException(EmBussinessError.NOT_AUTHORIZE);
        }
        int count= serverMapper.deleteServer(host);
        return count;
    }

    public Integer deleteAllServer(String username,Integer userid,String password) throws BussinessException {
        vaildUser(userid,username,password);
        return serverMapper.deleteAllServer(userid);
    }



    public boolean testConnection(String host) throws BussinessException {
        Server server= serverMapper.findServer(host);
        if(server==null){
            throw new BussinessException(EmBussinessError.NOT_EXIST);
        }
        if(SSH.createSession(server.getRole(),server.getHost(),server.getPort(),server.getPassword())==null){
            throw new BussinessException(EmBussinessError.CONNECT_REFUSED);
        }
        return true;
    }
}
