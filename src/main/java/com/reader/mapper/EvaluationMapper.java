package com.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reader.entity.Evaluation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Flagship
 * @Date 2021/3/20 21:07
 * @Description
 */
public interface EvaluationMapper extends BaseMapper<Evaluation> {
    /**
     * @param start 开始
     * @param end 结束
     * @return 短评列表
     */
    public List<Evaluation> selectAllEvaluation(@Param("start") Integer start, @Param("rows") Integer rows);

    /**
     * 计算总评数量
     * @return 总短评数
     */
    public Integer countAllEvaluation();
}
