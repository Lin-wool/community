package com.wool.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WOOL
 * 页面分页类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaginationDTO {
    private List<QuestionDTO> questionDTOList;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<Integer>();

    /**
     * @param totalPage  问题总页数
     * @param page       第几页
     */
    public void setPagination(Integer totalPage, Integer page) {
        // 总页数
        this.page = page;
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        // 是否显示上一页
        showPrevious = page == 1 ? false : true;
        // 是否显示第一页
        showFirstPage = pages.contains(1) ? false : true;
        // 是否显示下一页
        showNext = page == totalPage ? false : true;
        // 是否显示最后一页
        showEndPage = pages.contains(totalPage) ? false : true;
    }

    public Integer getTotalPage(Integer totalCount, Integer size) {
        totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        return totalPage;
    }
}
