package com.nanxiaoqiao.community.controllers;

import com.nanxiaoqiao.community.dto.PaginationDTO;
import com.nanxiaoqiao.community.dto.QuestionDTO;
import com.nanxiaoqiao.community.mapper.QuestionMapper;
import com.nanxiaoqiao.community.mapper.UserMapper;
import com.nanxiaoqiao.community.model.User;
import com.nanxiaoqiao.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Resource(name = "userMapper")
    private UserMapper userMapper;
    @Resource(name="questionService")
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PaginationDTO pagination = questionService.list(1, 5);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
