package com.wool.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private List questionList;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages;


}
