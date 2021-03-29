package com.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.reader.entity.Book;
import com.reader.entity.Evaluation;
import com.reader.entity.MemberReadState;
import com.reader.mapper.BookMapper;
import com.reader.mapper.EvaluationMapper;
import com.reader.mapper.MemberReadStateMapper;
import com.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @Author Flagship
 * @Date 2021/3/19 9:03
 * @Description
 */
@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

    /**
     * 分页查询图书
     * @param page 页号
     * @param rows 每页记录数
     * @return 分页对象
     */
    @Override
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String key = "c" + categoryId + "o" + order + "p" + page + "r" + rows;
        if (jedis.get(key) != null) {
            String value = jedis.get(key);
            return  (new Gson().fromJson(value, Page.class));
        }
        Page<Book> p = new Page<Book>(page, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        if (categoryId != null && categoryId != -1) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (order != null) {
            if ("quantity".equals(order)) {
                queryWrapper.orderByDesc("evaluation_quantity");
            } else if ("score".equals(order)) {
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        String value = new Gson().toJson(pageObject);
        jedis.set(key, value);
        return pageObject;
    }

    /**
     * 根据图书编号查询图书对象
     *
     * @param bookId 图书编号
     * @return 图书对象
     */
    @Override
    public Book selectById(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    /**
     * 更新图书评分/评价数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    /**
     * 创建新的图书
     *
     * @param book 图书对象
     * @return 图书对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    /**
     * 更新图书
     *
     * @param book 新图书数据
     * @return 更新后的数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    /**
     * 删除图书及相关数据
     *
     * @param bookId 图书编号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);

        QueryWrapper<MemberReadState> mrsQueryWrapper = new QueryWrapper<MemberReadState>();
        mrsQueryWrapper.eq("book_id", bookId);
        memberReadStateMapper.delete(mrsQueryWrapper);

        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<Evaluation>();
        evaluationQueryWrapper.eq("book_id", bookId);
        evaluationMapper.delete(evaluationQueryWrapper);
    }
}
