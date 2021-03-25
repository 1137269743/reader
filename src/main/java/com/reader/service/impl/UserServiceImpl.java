package com.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.reader.entity.User;
import com.reader.mapper.UserMapper;
import com.reader.service.UserService;
import com.reader.service.exception.BusinessException;
import com.reader.utils.Md5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Flagship
 * @Date 2021/3/24 8:03
 * @Description
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 管理员登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    @Override
    public User checkLogin(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException("U01", "用户名或密码不存在");
        }
        String md5 = Md5Utils.md5Digest(password, user.getSalt());
        if (!md5.equals(user.getPassword())) {
            throw new BusinessException("U01", "用户名或密码不存在");
        }
        return user;
    }
}
