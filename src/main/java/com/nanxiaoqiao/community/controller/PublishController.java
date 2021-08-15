package com.nanxiaoqiao.community.controller;

import com.nanxiaoqiao.community.dto.QuestionDTO;
import com.nanxiaoqiao.community.mapper.QuestionMapper;
import com.nanxiaoqiao.community.mapper.UserMapper;
import com.nanxiaoqiao.community.model.Question;
import com.nanxiaoqiao.community.model.User;
import com.nanxiaoqiao.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Resource(name = "userMapper")
    private UserMapper userMapper;
    @Resource(name = "questionService")
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title", required = false)String title,
                            @RequestParam(value = "description", required = false)String description,
                            @RequestParam(value = "tag", required = false)String tag,
                            @RequestParam(value = "id", required = false)Integer id,
                            HttpServletRequest request,
                            Model model) {
        // 回显
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        // 空值验证
        if (title == null || StringUtils.isEmpty(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || StringUtils.isEmpty(description)) {
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        // 入库
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.updateOrCreate(question);
        return "redirect:/";
    }

    /**
     * 编辑文章，路径带id
     * @param id
     * @param model
     * @return
     */
    @GetMapping("publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,
                     Model model) {
        QuestionDTO question = questionService.getQuestionDtoById(id);
        model.addAttribute("id", id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        return "publish";
    }
}
