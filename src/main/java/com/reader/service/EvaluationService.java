package com.reader.service;

import com.reader.entity.Evaluation;

import java.util.List;

/**
 * @Author Flagship
 * @Date 2021/3/20 21:08
 * @Description
 */
public interface EvaluationService {
    /**
     * 按图书编号查询有效短评
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId);

    /**
     * 查询所有评论
     * @param page 页号
     * @param rows 每页记录数
     * @return 评论列表
     */
    public List<Evaluation> selectAllEvaluation(Integer page, Integer rows);

    /**
     * 返回短评总条数
     * @return 短评总条数
     */
    public Integer countAllEvaluation();

    /**
     * 禁用评论
     * @param id 评论id
     * @param reason 禁用原因
     * @return 评论对象
     */
    public Evaluation disableEvaluation(Long id, String reason);
}
