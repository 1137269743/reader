package com.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.reader.entity.User;
import com.reader.mapper.UserMapper;
import com.reader.utils.Md5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Author Flagship
 * @Date 2021/3/24 8:03
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserServiceImplTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void checkLogin() {
        String username = "root";
        String password = "root1";
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            System.out.println("该用户不存在");
            return;
        }
        String md5 = Md5Utils.md5Digest(password, user.getSalt());
        if (!md5.equals(user.getPassword())) {
            System.out.println("密码错误");
            return;
        }
        System.out.println("登录成功");
    }
}