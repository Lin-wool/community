package com.wool.community.mapper;

import com.wool.community.model.Comment;

/**
 * 自定义评论方法
 * @author WOOL
 */
public interface CommentExtMapper {
    void incCommentCount(Comment dbComent);
}
