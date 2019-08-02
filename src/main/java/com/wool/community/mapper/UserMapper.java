package com.wool.community.mapper;

import com.wool.community.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @author WOOL
 * user类得mapper文件
 */
public interface UserMapper {
    /**
     * 添加user
     *
     * @param user
     * @return
     */
    @Insert("INSERT INTO user(ACCOUNT_ID,NAME,TOKEN,GMT_CREATE,GMT_MODIFIED,BIO,AVATAR_URL) VALUES(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    Long insertUser(User user);

    /**
     * 通过token查找用户
     *
     * @param token
     * @return
     */
    @Select("SELECT * FROM user WHERE TOKEN=#{token}")
    User findByToken(@Param("token") String token);

    /**
     * 通过ID查找用户
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM user WHERE ID=#{id}")
    User findById(@Param("id") Long id);

    /**
     * 通过accountId查找用户
     *
     * @param accountId
     * @return
     */
    @Select("SELECT * FROM user WHERE ACCOUNT_ID = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    /**
     * 通过ID更新用户得token
     *
     * @param user
     */
    @Update("UPDATE user set token=#{token} where id = #{id}")
    void updateToken(User user);
}
