package com.wool.community.dto;

import com.wool.community.model.User;
import lombok.Data;

/**
 * @author WOOL
 * 问题类页面传输模型
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Long commentCount;
    private Long viewCount;
    private Long likeCount;
    private Long dislikeCount;
    private String tag;
    private User user;


}
