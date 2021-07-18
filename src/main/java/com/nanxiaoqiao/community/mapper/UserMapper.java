package com.nanxiaoqiao.community.mapper;

import com.nanxiaoqiao.community.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Update("insert into user (id, account_id, name, token, gmt_create, gmt_modify)" +
            " values(#{user.id}, #{user.accountId}, #{user.name}, #{user.token}, #{user.gmtCreate}, #{user.gmtModify})")
    void insert(@Param("user") User user);
}
