package com.wool.community.service;

import com.wool.community.dto.QuestionDTO;
import com.wool.community.mapper.QuestionMapper;
import com.wool.community.mapper.UserMapper;
import com.wool.community.model.Question;
import com.wool.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WOOL
 * 问题得业务处理层
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 返回问题传输对象（包含用户信息）
     *
     * @return
     * @param page
     * @param size
     */
    public List<QuestionDTO> list(Integer page, Integer size) {
        Integer offset = (page-1)*size;
        Integer totalCount = questionMapper.count();
        List<Question> questionList = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
