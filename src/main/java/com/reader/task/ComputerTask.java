package com.reader.task;

import com.reader.service.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author Flagship
 * @Date 2021/3/21 18:20
 * @Description 完成自动计算任务
 */
@Component
public class ComputerTask {
    @Resource
    private BookService bookService;
    @Scheduled(cron = "0 * * * * ?")
    public void updateEvaluation() {
        bookService.updateEvaluation();
        System.out.println("已更新所有图书评分");
    }
}
