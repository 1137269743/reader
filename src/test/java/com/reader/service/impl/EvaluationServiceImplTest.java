package com.reader.service.impl;

import com.reader.entity.Evaluation;
import com.reader.mapper.EvaluationMapper;
import com.reader.utils.MyBatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author Flagship
 * @Date 2021/3/22 17:06
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class EvaluationServiceImplTest {
    @Resource
    private EvaluationMapper evaluationMapper;

    @Test
    public void selectAllEvaluation() throws IOException {
        List<Evaluation> evaluationList = evaluationMapper.selectAllEvaluation(1,10);

        for (Evaluation evaluation : evaluationList) {
            System.out.println(evaluation.getContent() + ":" + evaluation.getMember().getUsername() + ":" + evaluation.getBook().getBookName());
        }
        System.out.println("总条数：" + evaluationMapper.countAllEvaluation());
    }
}