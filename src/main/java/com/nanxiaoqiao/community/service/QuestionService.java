package com.nanxiaoqiao.community.service;

import com.nanxiaoqiao.community.dto.PaginationDTO;
import com.nanxiaoqiao.community.dto.QuestionDTO;
import com.nanxiaoqiao.community.mapper.QuestionMapper;
import com.nanxiaoqiao.community.mapper.UserMapper;
import com.nanxiaoqiao.community.model.Question;
import com.nanxiaoqiao.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("questionService")
public class QuestionService {
    @Resource(name = "questionMapper")
    private QuestionMapper questionMapper;
    @Resource(name = "userMapper")
    private UserMapper userMapper;

    public PaginationDTO list(int page, int size) {
        int offset = (page - 1) * size;
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question: questions) {
            Integer id = question.getCreator();
            User user = userMapper.findUserById(id);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        PaginationDTO pagination = new PaginationDTO();
        int totalCount = questionMapper.getTotalCount();
        pagination.setQuestions(questionDTOS);
        pagination.setPagination(totalCount, page, size);
        return pagination;
    }

}
