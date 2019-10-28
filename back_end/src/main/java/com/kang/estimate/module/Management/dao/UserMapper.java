package com.kang.estimate.module.Management.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kang.estimate.module.Management.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username=#{username}")
    User findUser(@Param("username") String username);

    @Insert("insert into user(username,password) values(#{username},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int addUser(User user);

    @Delete("delete from user where userid=#{userid}")
    int deleteUser(User user);
}
