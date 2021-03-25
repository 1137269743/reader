package com.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.reader.entity.Book;
import com.reader.entity.Evaluation;
import com.reader.entity.Member;
import com.reader.mapper.BookMapper;
import com.reader.mapper.EvaluationMapper;
import com.reader.mapper.MemberMapper;
import com.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author Flagship
 * @Date 2021/3/20 21:09
 * @Description
 */
@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;
    /**
     * 按图书编号查询有效短评
     * @param bookId 图书编号
     * @return 评论列表
     */
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("state", "enable");
        queryWrapper.orderByDesc("enjoy");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        for (Evaluation evaluation : evaluationList) {
            Member member = memberMapper.selectById(evaluation.getMemberId());
            evaluation.setMember(member);
            evaluation.setBook(book);
        }
        return evaluationList;
    }

    /**
     * 查询所有评论
     *
     * @param page 页号
     * @param rows 每页记录数
     * @return 评论列表
     */
    @Override
    public List<Evaluation> selectAllEvaluation(Integer page, Integer rows) {
        int start = (page - 1) * rows;
        List<Evaluation> evaluationList = evaluationMapper.selectAllEvaluation(start,rows);
        return evaluationList;
    }

    /**
     * 返回短评总条数
     *
     * @return 短评总条数
     */
    @Override
    public Integer countAllEvaluation() {
        return evaluationMapper.countAllEvaluation();
    }

    /**
     * 禁用评论
     *
     * @param id     评论id
     * @param reason 禁用原因
     * @return 评论对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Evaluation disableEvaluation(Long id, String reason) {
        Evaluation evaluation = evaluationMapper.selectById(id);;

        evaluation.setDisableReason(reason);
        evaluation.setState("disable");
        evaluation.setDisableTime(new Date());
        evaluationMapper.updateById(evaluation);
        return evaluation;
    }
}
