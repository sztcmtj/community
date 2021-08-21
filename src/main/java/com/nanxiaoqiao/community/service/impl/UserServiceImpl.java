package com.nanxiaoqiao.community.service.impl;

import com.nanxiaoqiao.community.mapper.UserMapper;
import com.nanxiaoqiao.community.model.User;
import com.nanxiaoqiao.community.model.UserExample;
import com.nanxiaoqiao.community.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource(name = "userMapper")
    UserMapper userMapper;

    @Override
    public void updateOrCreate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        if (dbUsers.size() > 0) {
            // 更新
            User dbUser = dbUsers.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setName(user.getName());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setToken(user.getToken());
            UserExample updateExample = new UserExample();
            updateExample.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, updateExample);
        } else {
            // 创建
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
        }
    }
}
