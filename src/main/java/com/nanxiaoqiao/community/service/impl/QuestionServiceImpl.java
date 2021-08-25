package com.nanxiaoqiao.community.service.impl;

import com.nanxiaoqiao.community.dto.PaginationDTO;
import com.nanxiaoqiao.community.dto.QuestionDTO;
import com.nanxiaoqiao.community.exception.CustomizeErrorCode;
import com.nanxiaoqiao.community.exception.CustomizeException;
import com.nanxiaoqiao.community.mapper.QuestionMapper;
import com.nanxiaoqiao.community.mapper.UserMapper;
import com.nanxiaoqiao.community.model.Question;
import com.nanxiaoqiao.community.model.QuestionExample;
import com.nanxiaoqiao.community.model.User;
import com.nanxiaoqiao.community.service.QuestionService;
import org.apache.ibatis.session.RowBounds;
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
        int totalCount = (int) questionMapper.countByExample(new QuestionExample());
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
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),
                new RowBounds(offset, size));
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question: questions) {
            Integer id = question.getCreator();
            User user = userMapper.selectByPrimaryKey(id);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        pagination.setQuestionDTOs(questionDTOS);

        return pagination;
    }


    @Override
    public PaginationDTO listByUserId(Integer userId, Integer page, Integer size) {
        PaginationDTO pagination = new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        int totalCount = (int) questionMapper.countByExample(questionExample);
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
        questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,
                new RowBounds(offset, size));
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question: questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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
        Question question = questionMapper.selectByPrimaryKey(questionId);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void updateOrCreate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insert(question);
        } else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setId(question.getId());
            int updated = questionMapper.updateByPrimaryKeySelective(updateQuestion);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

        }
    }

}
