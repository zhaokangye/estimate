package com.kang.estimate.module.deploy.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kang.estimate.module.deploy.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author kang
 */
@Mapper
public interface FileMapper extends BaseMapper<FileEntity> {

    FileEntity selectByPath(@Param("path") String path);

    List<FileEntity> selectFileList(@Param("createBy") Integer createBy);
}
