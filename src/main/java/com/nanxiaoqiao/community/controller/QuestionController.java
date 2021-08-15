package com.nanxiaoqiao.community.controller;

import com.nanxiaoqiao.community.dto.QuestionDTO;
import com.nanxiaoqiao.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * @Author mutianjie
 * @Date 2021/8/1 2:02 下午
 * @Version 1.0
 **/
@Controller
public class QuestionController {
    @Resource(name="questionService")
    QuestionService questionService;

    @GetMapping("/question/{questionId}")
    public String question(@PathVariable(name="questionId") Integer questionId,
                           Model model) {
        QuestionDTO questionDTO = questionService.getQuestionDtoById(questionId);
        model.addAttribute("questionDTO", questionDTO);
        return "question";
    }
}
