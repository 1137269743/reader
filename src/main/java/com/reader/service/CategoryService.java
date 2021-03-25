package com.reader.service;

import com.reader.entity.Category;

import java.util.List;

/**
 * @Author Flagship
 * @Date 2021/3/19 8:30
 * @Description
 */
public interface CategoryService {
    /**
     * 查询所有图书分类
     * @return 图书分类List
     */
    public List<Category> selectAll();
}
