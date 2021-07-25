package com.nanxiaoqiao.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    // 是否显示前一页
    private boolean hasPrevious;
    // 是否显示首页
    private boolean showFirstPage;
    // 是否显示下一页
    private boolean hasNext;
    // 是否显示尾页
    private boolean showEndPage;

    private List<QuestionDTO> questions;
    // 所有展示的页号
    private List<Integer> pages = new ArrayList<>();
    // 当前请求指定的页号
    private int page;

    public void setPagination(int totalCount, int page, int size) {
        this.hasPrevious = page != 1;
        this.hasNext = page != totalCount;
        this.showFirstPage = !pages.contains(1);
        this.showEndPage = !pages.contains(totalCount);

        this.pages.add(page);
        int totalPage = 0;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        for (int i = 1; i <= 3; i++) {
            if (page - i >= 1) {
                pages.add(page - i, 0);
            }

            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
    }
}
