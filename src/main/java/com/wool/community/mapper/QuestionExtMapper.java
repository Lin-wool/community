package com.wool.community.mapper;

import com.wool.community.model.Question;
import com.wool.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author WOOL
 * 避免每次调用mybatis generator得时候覆盖掉我们自己定义的方法，则重新写了一个！
 */
public interface QuestionExtMapper {
    /**
     * 添加阅读数
     * @param record
     * @return
     */
    int incView(Question record);
    int incComment(Question question);
    List<Question> selectRelated(Question question);
}