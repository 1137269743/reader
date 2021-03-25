package com.reader.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @Author Flagship
 * @Date 2020/12/8 18:15
 * @Description 单例模式-饿汉式实现MyBatis工具类
 */
public class MyBatisUtils {
    /**
     * 利用单例对象
     */
    private static SqlSessionFactory sqlSessionFactory = null;

    /**
     * 实例化单例对象
     */
    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
            //抛出异常通知调用者
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 创建一个新的SqlSession对象
     * @return SqlSession对象
     */
    public static SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }

    /**
     * 释放一个有效的SqlSession对象
     * @param session 准备释放SqlSession对象
     */
    public static void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
        }
    }
}
