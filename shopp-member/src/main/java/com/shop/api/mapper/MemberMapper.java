package com.shop.api.mapper;

import com.shop.entity.UserEntity;
import org.apache.ibatis.annotations.*;

/**
 * @author admin
 */
@Mapper
public interface MemberMapper {
    /**
     * 通过用户id查找用户
     * @param userId
     * @return user
     */
    @Select("select  id,username,password,phone,email,created,updated,openid from mb_user where id =#{userId}")
    UserEntity findById(@Param("userId") Long userId);

    /**
     * 插入用户
     * @param userEntity
     * @return success
     */
    @Insert("INSERT  INTO `mb_user`  (username,password,phone,email,created,updated,openid) VALUES (#{username}, #{password},#{phone},#{email},#{created},#{updated},#{openid});")
    Integer insertUser(UserEntity userEntity);


    /**
     *  登录
     * @param username
     * @param password
     * @return
     */
    @Select("select  id,username,password,phone,email,created,updated,openid from mb_user where username=#{username} and password=#{password}")
    UserEntity login(@Param("username") String username,@Param("password")String password);

    /**
     * 通过openid查询用户
     * @param openid
     * @return
     */
    @Select("select  id,username,password,phone,email,created,updated,openid from mb_user where openid =#{openid}")
    UserEntity findByOpenId(@Param("openid") String openid);

    /**
     * 通过openid修改用户
     * @param openid
     * @return
     */
    @Update("update mb_user set openid=#{openid} where id=#{userid}")
    Integer updateByOpenIdUser(@Param("openid") String openid,@Param("userid") Integer userid);
}
