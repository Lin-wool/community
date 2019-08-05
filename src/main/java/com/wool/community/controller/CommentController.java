package com.wool.community.controller;

import com.wool.community.dto.CommentDTO;
import com.wool.community.dto.ResultDTO;
import com.wool.community.exception.CustomizeErrorCode;
import com.wool.community.mapper.CommentMapper;
import com.wool.community.model.Comment;
import com.wool.community.model.User;
import com.wool.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WOOL
 * 评论控制器
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/comment")
    @ResponseBody
    public Object comment(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setLikeCount(0L);
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
