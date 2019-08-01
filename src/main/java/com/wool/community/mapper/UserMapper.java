package com.wool.community.mapper;

import com.wool.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author WOOL
 * user类得mapper文件
 */
public interface UserMapper {
    /**
     * 添加user
     * @param user
     * @return
     */
    @Insert("INSERT INTO user(ACCOUNT_ID,NAME,TOKEN,GMT_CREATE,GMT_MODIFIED) VALUES(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    Long insertUser(User user);

    /**
     * 通过token查找用户
     * @param token
     * @return
     */
    @Select("SELECT * FROM user WHERE TOKEN=#{token}")
    User findByToken(@Param("token") String token);
}
