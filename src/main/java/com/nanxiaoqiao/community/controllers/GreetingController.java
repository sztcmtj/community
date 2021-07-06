package com.nanxiaoqiao.community.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

/**
 * @Author mutianjie
 * @Date 2021/7/6 12:56 下午
 * @Version 1.0
 **/
@Controller
public class GreetingController {
    @RequestMapping("/greet")
    public String greet(@RequestParam(name = "name", required = false, defaultValue = "World")String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
}
