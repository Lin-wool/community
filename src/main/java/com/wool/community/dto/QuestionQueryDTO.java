package com.wool.community.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author WOOL
 * 查询问题得传输数据模型
 *
 */
@Data
@ToString
public class QuestionQueryDTO {
    private String search;
    private Integer offset;
    private Integer size;
}
