package com.reader.controller.management;

import com.reader.entity.Evaluation;
import com.reader.service.EvaluationService;
import com.reader.service.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Flagship
 * @Date 2021/3/22 16:47
 * @Description 评论管理控制器
 */
@Controller
@RequestMapping("/management/evaluation")
public class ManageEvaluationController {
    @Resource
    private EvaluationService evaluationService;

    @GetMapping("/index.html")
    public ModelAndView showBook() {
        ModelAndView mav = new ModelAndView("/management/evaluation");
        return mav;
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        List<Evaluation> list = evaluationService.selectAllEvaluation(page, limit);
        int count = evaluationService.countAllEvaluation();
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", list);
        result.put("count", count);
        return result;
    }

    @PostMapping("/disable/{id}")
    @ResponseBody
    public Map disable(@PathVariable("id") Long id, String reason) {
        Map result = new HashMap();
        try {
            evaluationService.disableEvaluation(id, reason);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }
}
