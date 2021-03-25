package com.reader.service;

import com.reader.entity.User;

/**
 * @Author Flagship
 * @Date 2021/3/24 8:01
 * @Description
 */
public interface UserService {
    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    public User checkLogin(String username, String password);
}
