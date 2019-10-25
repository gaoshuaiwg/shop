package com.shop.api.mapper;

import com.shop.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    @Select("select  id,username,password,phone,email,created,updated from mb_user where id =#{userId}")
    UserEntity findById(@Param("userId") Long userId);

    /**
     * 插入用户
     * @param userEntity
     * @return success
     */
    @Insert("INSERT  INTO `mb_user`  (username,password,phone,email,created,updated) VALUES (#{username}, #{password},#{phone},#{email},#{created},#{updated});")
    Integer insertUser(UserEntity userEntity);


    /**
     *  登录
     * @param username
     * @param password
     * @return
     */
    @Select("select  id,username,password,phone,email,created,updated from mb_user where username=#{username} and password=#{password}")
    UserEntity login(@Param("username") String username,@Param("password")String password);
}
