package com.nanxiaoqiao.community.service;

import com.nanxiaoqiao.community.dto.PaginationDTO;
import com.nanxiaoqiao.community.dto.QuestionDTO;
import com.nanxiaoqiao.community.model.Question;

public interface QuestionService {
    PaginationDTO list(int page, int size);

    PaginationDTO listByUserId(Integer id, Integer page, Integer size);

    QuestionDTO getQuestionDtoById(Integer questionId);

    void updateOrCreate(Question question);
}
