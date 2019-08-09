package com.wool.community.service;

import com.wool.community.dto.CommentDTO;
import com.wool.community.enums.CommentTypeEnum;
import com.wool.community.enums.NotificationTypeEnum;
import com.wool.community.enums.NotificationStatusEnum;
import com.wool.community.exception.CustomizeErrorCode;
import com.wool.community.exception.CustomizeException;
import com.wool.community.mapper.*;
import com.wool.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Transactional(rollbackFor = Exception.class)
    public void insert(Comment comment, User creator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.QUESTION.getType()) {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1L);
            commentMapper.insertSelective(comment);

            // 创建通知
            createNotification(comment, question.getCreator(), creator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUETION, question.getId());
            questionExtMapper.incComment(question);
        } else {
            // 回复评论
            Comment dbComent = commentMapper.selectByPrimaryKey(comment.getParentId());
            Question question = questionMapper.selectByPrimaryKey(dbComent.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            if (dbComent == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            dbComent.setCommentCount(1L);
            commentExtMapper.incCommentCount(dbComent);
            // 创建通知
            createNotification(comment, dbComent.getCommentator(), creator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
        }

    }

    private void createNotification(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        if (comment.getCommentator() == receiver) {
            return;
        }
        Notification notification = new Notification();
        notification.setReceiver(receiver);
        notification.setOuterTitle(outerTitle);
        notification.setNotifierName(notifierName);
        notification.setOuterid(outerId);
        notification.setType(notificationType.getType());
        notification.setNotifier(comment.getCommentator());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum commentTypeEnum) {
        // 通过问题ID（parentId）获取问题评论
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(commentTypeEnum.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取去重得评论人lombda
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        // 获取评论人并转换成map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 转换 comment为commentDTO

        List<CommentDTO> commentDTOList = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOList;
    }
}
