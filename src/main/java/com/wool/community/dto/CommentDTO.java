package com.wool.community.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
