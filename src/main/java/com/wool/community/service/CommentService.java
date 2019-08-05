package com.wool.community.service;

import com.wool.community.enums.CommentTypeEnum;
import com.wool.community.exception.CustomizeErrorCode;
import com.wool.community.exception.CustomizeException;
import com.wool.community.mapper.CommentMapper;
import com.wool.community.mapper.QuestionExtMapper;
import com.wool.community.mapper.QuestionMapper;
import com.wool.community.model.Comment;
import com.wool.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if ( comment.getType() == CommentTypeEnum.QUESTION.getType()){
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1L);
            questionExtMapper.incComment(question);
        }else{
            // 回复评论
            Comment com = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(com==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
        }
        commentMapper.insert(comment);
    }
}
