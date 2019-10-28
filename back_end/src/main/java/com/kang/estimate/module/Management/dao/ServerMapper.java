package com.kang.estimate.module.Management.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kang.estimate.module.Management.entity.Server;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ServerMapper extends BaseMapper<Server> {

    @Select("select * from server where host=#{host}")
    Server findServer(@Param("host") String host);

    List<Server> findServerByUserid(@Param("userid") int userid);

    @Insert("insert into server values(#{host},#{port},#{role},#{password},#{servername},#{userid})")
    void addServer(Server server);

    @Delete("delete from server where host=#{host}")
    int deleteServer(@Param("host") String host);

    @Delete("delete from server where userid=#{userid}")
    int deleteAllServer(@Param("userid") int userid);

}
