package com.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reader.entity.Book;

/**
 * @Author Flagship
 * @Date 2021/3/19 9:01
 * @Description
 */
public interface BookMapper extends BaseMapper<Book> {
    /**
     * 更新图书评分/评价数量
     */
    public void updateEvaluation();
}
