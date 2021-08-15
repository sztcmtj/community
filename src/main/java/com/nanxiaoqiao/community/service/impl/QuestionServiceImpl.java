package com.nanxiaoqiao.community.service.impl;

import com.nanxiaoqiao.community.dto.PaginationDTO;
import com.nanxiaoqiao.community.dto.QuestionDTO;
import com.nanxiaoqiao.community.mapper.QuestionMapper;
import com.nanxiaoqiao.community.mapper.UserMapper;
import com.nanxiaoqiao.community.model.Question;
import com.nanxiaoqiao.community.model.User;
import com.nanxiaoqiao.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
    @Resource(name = "questionMapper")
    private QuestionMapper questionMapper;
    @Resource(name = "userMapper")
    private UserMapper userMapper;

    public PaginationDTO list(int page, int size) {
        PaginationDTO pagination = new PaginationDTO();
        int totalCount = questionMapper.count();
        // 计算总页数
        int totalPage = (totalCount % size == 0) ? (totalCount / size) : (totalCount / size + 1);
        pagination.setTotalPage(totalPage);
        // 范围限制
        if (page <= 0) {
            page = 1;
        } else if (page > pagination.getTotalPage()) {
            page = pagination.getTotalPage();
        }
        pagination.setPagination(page);
        int offset = Math.max((page - 1) * size, 0);
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

        pagination.setQuestionDTOs(questionDTOS);

        return pagination;
    }


    @Override
    public PaginationDTO listById(Integer id, Integer page, Integer size) {
        PaginationDTO pagination = new PaginationDTO();
        int totalCount = questionMapper.countByUserId(id);
        // 计算总页数
        int totalPage = (totalCount % size == 0) ? (totalCount / size) : (totalCount / size + 1);
        pagination.setTotalPage(totalPage);

        // 范围限制
        if (page <= 0) {
            page = 1;
        } else if (page > pagination.getTotalPage()) {
            page = pagination.getTotalPage();
        }

        pagination.setPagination(page);
        int offset = (page - 1) * size;
        List<Question> questions = questionMapper.listByUserId(id, offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question: questions) {
            User user = userMapper.findUserById(id);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        pagination.setQuestionDTOs(questionDTOS);

        return pagination;
    }

    @Override
    public QuestionDTO getQuestionDtoById(Integer questionId) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.getQuestionById(questionId);
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findUserById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void updateOrCreate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.create(question);
        } else {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }

}
