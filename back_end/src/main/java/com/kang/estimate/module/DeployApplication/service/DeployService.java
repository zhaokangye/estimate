package com.kang.estimate.module.DeployApplication.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.kang.estimate.module.Management.dao.ServerMapper;
import com.kang.estimate.module.Management.entity.Server;
import com.kang.estimate.core.error.BussinessException;
import com.kang.estimate.core.error.EmBussinessError;
import com.kang.estimate.utils.SSH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DeployService {

    @Autowired
    ServerMapper serverMapper;


    public StringBuilder execCommad(Integer userid,String host,String commad) throws InterruptedException, JSchException, IOException, BussinessException {
        Server server= serverMapper.findServer(host);
        if(!userid.equals(server.getUserid())){
            throw new BussinessException(EmBussinessError.NOT_AUTHORIZE);
        }
        Session session=SSH.createSession(server.getRole(),host,server.getPort(),server.getPassword());
        StringBuilder strResult=SSH.execCommad(session,commad);
        System.out.println(strResult);
        return strResult;
    }

    public boolean deployApplication(Integer userid,String src,String host,String dst) throws BussinessException, SftpException, JSchException {
        Server server= serverMapper.findServer(dst);
        if(!userid.equals(server.getUserid())){
            throw new BussinessException(EmBussinessError.NOT_AUTHORIZE);
        }
        Session session=SSH.createSession(server.getRole(),host,server.getPort(),server.getPassword());
        SSH.upload(session,src,dst);
        return true;
    }

}
