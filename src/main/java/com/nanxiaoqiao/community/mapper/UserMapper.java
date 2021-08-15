package com.nanxiaoqiao.community.mapper;

import com.nanxiaoqiao.community.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Update("insert into user (id, account_id, name, token, gmt_create, gmt_modified, avatar_url) values(#{user.id}, " +
            "#{user.accountId}, #{user.name}, #{user.token}, #{user.gmtCreate}, #{user.gmtModified}, #{user.avatarUrl})")
    void insertUser(@Param("user")User user);

    @Select("select * from user where token = #{token}")
    User findUserByToken(@Param("token")String token);

    @Select("select * from user where id = #{id}")
    User findUserById(@Param("id")Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findUserByAccountId(User user);

    @Update("update user set name = #{name}, token=#{token}, gmt_modified = #{gmtModified}, " +
            "avatar_url = #{avatarUrl} where account_id = #{accountId}")
    void update(User user);
}
