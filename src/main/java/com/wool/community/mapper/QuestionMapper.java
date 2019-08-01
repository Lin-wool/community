package com.wool.community.mapper;

import com.wool.community.model.Question;
import org.apache.ibatis.annotations.Insert;

/**
 * @author WOOL
 * 问题mapper
 */
public interface QuestionMapper {
    /**
     * 添加问题
     * @param question
     * @return
     */
    @Insert("INSERT INTO question(TITLE,DESCRIPTION,GMT_CREATE,GMT_MODIFIED,CREATOR,TAG) VALUES(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    Long insertQuestion(Question question);
}
