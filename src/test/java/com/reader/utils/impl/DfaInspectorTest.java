package com.reader.utils.impl;

import com.reader.service.EvaluationService;
import com.reader.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Flagship
 * @Date 2021/3/22 23:08
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DfaInspectorTest {
    @Resource
    private MemberService memberService;

    @Test
    public void test() throws Exception {
        String[] strings = {
                "这篇小说作者是谁，我要拜他为师！",
                "情节安排好巧妙哦！",
                "有点纠结，结局呢？！",
                "抓狂！文文也能把我感动！",
                "主人公到底怎么样了啊！好奇。。。",
                "这本书怎么有错字啊！衰…",
                "章节都混乱了，怎么回事啊！",
                "作者努力啊！偶等着你的新生作品呢！",
                "好喜欢看这篇文文哦！作者咋能写出这种东西呢！",
                "身临其境！好像我是主角一样耶！",
                "有没有番外呢？期待！",
                "支持作者！大家顶我啊！",
                "更的好慢啊！广大群众都等着呢！",
                "看了忽然很期待自己是主角，能这么幸运！",
                "说句实话其实还行。",
                "网文嘛，都那样，坐地铁的时候打发时间用的，就要不追求什么文学性了。",
                "一般吧，就那样"
        };
        for (int i = 1; i < 20000; i++) {
            Long mumId = Long.parseLong(String.valueOf((int)(1+Math.random()*(1000-1+1))));
            Long bookId = Long.parseLong(String.valueOf((int)(1+Math.random()*(1134-1+1))));
            String content = strings[(int) Math.floor(Math.random()*strings.length)];
            memberService.evaluate(mumId, bookId, (int)(1+Math.random()*(5-1+1)), content);
            System.out.println("第" + i + "条");
        }
    }
}