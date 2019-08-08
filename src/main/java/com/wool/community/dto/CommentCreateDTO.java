package com.wool.community.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
