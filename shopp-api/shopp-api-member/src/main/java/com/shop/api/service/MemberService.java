package com.shop.api.service;

import com.shop.base.ResponseBase;
import com.shop.entity.UserEntity;
import jdk.nashorn.internal.parser.Token;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author admin
 */
@RequestMapping("/member")
public interface MemberService {
    /**
     * 根据用户ID查询用户信息
     *
     * @param userId
     * @return user
     */
    @RequestMapping("/findUserById")
    ResponseBase findUserById(Long userId);

    /**
     * 用户注册
     *
     * @param userEntity
     * @return
     */
    @RequestMapping("/register")
    ResponseBase registerUser(@RequestBody UserEntity userEntity);

    /**
     * 用户登录
     *
     * @param userEntity
     * @return
     */
    @RequestMapping("/login")
    ResponseBase login(@RequestBody UserEntity userEntity);

    /**
     *  通过token查找用户信息
     * @param token
     * @return
     */
    @RequestMapping("/findByTokenUser")
    ResponseBase findByTokenUser(@RequestParam("token") String token);

    /**
     * 通过openid获取用户
     * @param openid
     * @return
     */
    @RequestMapping("/findByOpenIdUser")
    ResponseBase findByOpenIdUser(@RequestParam("openid") String openid);

    /**
     * qq登录
     * @param userEntity
     * @return
     */
    @RequestMapping("/qqLogin")
    ResponseBase qqLogin(@RequestBody UserEntity userEntity);
}
