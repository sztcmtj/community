package com.nanxiaoqiao.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    // 是否显示前一页
    private boolean showPrevious;
    // 是否显示首页
    private boolean showFirstPage;
    // 是否显示下一页
    private boolean showNext;
    // 是否显示尾页
    private boolean showEndPage;
    // 总页数
    private int totalPage;

    private List<QuestionDTO> questionDTOs;
    // 所有展示的页号
    private List<Integer> pages = new ArrayList<>();
    // 当前请求指定的页号
    private int page;

    public void setPagination(int page) {
        this.page = page;
        this.pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i >= 1) {
                pages.add(0, page - i);
            }

            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        this.showPrevious = page != 1;
        this.showNext = page != totalPage;
        this.showFirstPage = !pages.contains(1);
        this.showEndPage = !pages.contains(totalPage);
    }
}
