package com.wool.community.service;

import com.wool.community.dto.PaginationDTO;
import com.wool.community.dto.QuestionDTO;
import com.wool.community.mapper.QuestionMapper;
import com.wool.community.mapper.UserMapper;
import com.wool.community.model.Question;
import com.wool.community.model.QuestionExample;
import com.wool.community.model.User;
import com.wool.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
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
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        // 审核page大小
        if (page < 1) {
            page = 1;
        }

        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        Integer totalPage = paginationDTO.getTotalPage(totalCount, size);
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);
        Integer offset = (page - 1) * size;
        QuestionExample questionExample = new QuestionExample();
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question : questionList) {
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(example);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);


        return paginationDTO;
    }

    public PaginationDTO listByCreator(Long creator, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        // 审核page大小
        if (page < 1) {
            page = 1;
        }
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(creator);
        Integer totalCount = (int)questionMapper.countByExample(example);
        Integer totalPage = paginationDTO.getTotalPage(totalCount, size);
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);
        Integer offset = (page - 1) * size;
        example.createCriteria().andCreatorEqualTo(creator);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question : questionList) {
            UserExample example1 = new UserExample();
            example.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(example1);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);


        return paginationDTO;
    }

    public QuestionDTO findById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        if (question != null) {
            Long creator = question.getCreator();
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(creator);
            List<User> users = userMapper.selectByExample(example);
            questionDTO.setUser(users.get(0));
        }
        return questionDTO;
    }

    /**
     * 插入或修改问题
     * @param question
     */
    public void createOrUpdate(Question question) {
        if(question.getId()!=null){
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question, example);
        }else {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }
    }
}
