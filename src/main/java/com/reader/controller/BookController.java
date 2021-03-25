package com.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.reader.entity.*;
import com.reader.service.BookService;
import com.reader.service.CategoryService;
import com.reader.service.EvaluationService;
import com.reader.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author Flagship
 * @Date 2021/3/19 8:36
 * @Description 图书控制器
 */
@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private MemberService memberService;

    /**
     * 显示首页
     * @return 返回视图
     */
    @GetMapping("/")
    public ModelAndView showIndex() {
        ModelAndView mav = new ModelAndView("/index");
        List<Category> categoryList = categoryService.selectAll();
        mav.addObject("categoryList", categoryList);
        return mav;
    }

    /**
     * 分页查询图书列表
     * @param p 页号
     * @return 分页对象
     */
    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> selectBook(Long categoryId, String order, Integer p) {
        if (p == null) {
            p = 1;
        }
        if (order == null) {
            order = "quantity";
        }
        IPage<Book> pageObject = bookService.paging(categoryId, order, p, 10);
        return pageObject;
    }

    @GetMapping("/book/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long id, HttpSession session) {
        Book book = bookService.selectById(id);
        List<Evaluation> evaluationList = evaluationService.selectByBookId(id);
        Member member = (Member) session.getAttribute("loginMember");
        ModelAndView mav = new ModelAndView("/detail");
        if (member != null) {
            //获取会员阅读状态
            MemberReadState memberReadState = memberService.selectMemberReadState(member.getMemberId(), id);
            mav.addObject("memberReadState", memberReadState);
        }
        System.out.println(evaluationList.get(0).getEvaluationId());
        mav.addObject("book", book);
        mav.addObject("evaluationList", evaluationList);
        return mav;
    }
}
