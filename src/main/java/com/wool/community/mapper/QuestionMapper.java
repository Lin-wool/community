package com.wool.community.mapper;

import com.wool.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author WOOL
 * 问题mapper
 */
public interface QuestionMapper {
    /**
     * 添加问题
     *
     * @param question
     * @return
     */
    @Insert("INSERT INTO question(TITLE,DESCRIPTION,GMT_CREATE,GMT_MODIFIED,CREATOR,TAG) VALUES(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    Long insertQuestion(Question question);

    /**
     * 分页查出问题
     *
     * @param offset
     * @param size
     * @return
     */
    @Select("SELECT * FROM question LIMIT #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    /**
     * 查询问题总数
     * @return
     */
    @Select("SELECT COUNT(1) FROM question")
    Integer count();
}
