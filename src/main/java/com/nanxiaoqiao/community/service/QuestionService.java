package com.nanxiaoqiao.community.service;

import com.nanxiaoqiao.community.dto.PaginationDTO;

public interface QuestionService {
    PaginationDTO list(int page, int size);

    PaginationDTO list(Integer id, Integer page, Integer size);
}
