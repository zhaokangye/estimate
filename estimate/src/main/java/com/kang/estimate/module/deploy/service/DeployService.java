package com.kang.estimate.module.deploy.service;

import com.kang.estimate.core.error.BussinessException;
import com.kang.estimate.core.error.EmBussinessError;
import com.kang.estimate.core.shiro.ShiroKit;
import com.kang.estimate.module.deploy.dao.FileMapper;
import com.kang.estimate.module.deploy.entity.FileEntity;
import com.kang.estimate.module.management.dao.ServerMapper;
import com.kang.estimate.module.management.entity.Server;
import com.kang.estimate.util.Const;
import com.kang.estimate.util.Ftp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
/**
 * @author kang
 */
public class DeployService {

    @Autowired
    private ServerMapper serverMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private ShiroKit shiroKit;

    public String findWebappsPath(String host){
        return Ftp.getFtpUtil(findServer(host)).findWebappsPath();
    }

    public String findBinPath(String host){
        return Ftp.getFtpUtil(findServer(host)).findBinPath();
    }

    public List<FileEntity> selectFileList(){
        return fileMapper.selectFileList(shiroKit.getId());
    }

    public String uploadFile(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String filePath = Const.UPLOAD_PATH;
        String path=filePath+fileName;
        if(fileMapper.selectByPath(path)!=null){
            throw new BussinessException(EmBussinessError.REPEAT_UPLOAD);
        }
        File dest = new File(path);
        try {
            file.transferTo(dest);
            // 文件路径和大小记录到数据库
            FileEntity fileEntity=new FileEntity();
            fileEntity.setPath(filePath);
            fileEntity.setFileName(fileName);
            fileEntity.setTotalSize(file.getSize());
            fileEntity.setCreateBy(shiroKit.getId());
            fileEntity.setCreateTime(new Date());
            fileMapper.insert(fileEntity);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BussinessException(EmBussinessError.UNKNOW_ERROR);
        }
    }

    public boolean deployApp(String host,String src,String dst){
        FileEntity fileEntity=fileMapper.selectByPath(src);
        if(fileEntity!=null&&fileEntity.getTotalSize()!=null){
            Ftp.getFtpUtil(findServer(host)).upload(src,dst,fileEntity.getTotalSize());
            return true;
        }
        throw new BussinessException(EmBussinessError.NOT_EXIST);
    }

    public Server findServer(String host){
        Integer userId=shiroKit.getId();
        Server server=serverMapper.selectByHost(userId,host);
        if(server==null){
            throw new BussinessException(EmBussinessError.NOT_EXIST);
        }
        return server;
    }

    public BigDecimal uploadProgress(String key){
        if(Const.UPLOAD_PERCENTAGE.containsKey(key)){
            BigDecimal percentage=Const.UPLOAD_PERCENTAGE.get(key);
            return percentage;
        }
        throw new BussinessException(EmBussinessError.NOT_EXIST);
    }

}
