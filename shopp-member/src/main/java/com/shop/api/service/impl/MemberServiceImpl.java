package com.shop.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.shop.api.mapper.MemberMapper;
import com.shop.api.service.MemberService;
import com.shop.base.BaseApiService;
import com.shop.base.ResponseBase;
import com.shop.constant.Constants;
import com.shop.entity.UserEntity;
import com.shop.mq.RegisterMailboxProducer;
import com.shop.utils.MD5Util;
import com.shop.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author admin
 */
@RestController
@Slf4j
public class MemberServiceImpl extends BaseApiService implements MemberService {
    @Value("${messages.queue}")
    private String MESSAGESQUEUE;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;

    @Override
    public ResponseBase findUserById(Long userId) {
        UserEntity user = memberMapper.findById(userId);
        if (user == null) {
            return setResultError("查找用户失败");
        }
        return setResultSuccess(user);
    }

    @Override
    public ResponseBase registerUser(@RequestBody UserEntity userEntity) {
        //参数验证
        String password = userEntity.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空");
        }
        //MD5加盐
        String newPassword = MD5Util.MD5(password);
        userEntity.setPassword(newPassword);
        userEntity.setCreated(new Date());
        userEntity.setUpdated(new Date());
        Integer integer = memberMapper.insertUser(userEntity);
        if (integer <= 0) {
            return setResultError("注册用户信息失败");
        }

        //采用异步方式发送消息
        String email = userEntity.getEmail();
        String message = message(email);
        log.info("===会员服务推送消息到消息服务平台===接送：{}", message);
        sendMsg(message);
        return setResultSuccess("用户注册成功");
    }

    /**
     * 用户登录
     *
     * @param userEntity 用户对象
     * @return responseBase
     */
    @Override
    public ResponseBase login(@RequestBody UserEntity userEntity) {
        //1.验证参数
        String username = userEntity.getUsername();
        if (StringUtils.isEmpty(username)) {
            return setResultError("用户名不能为空！");
        }
        String password = userEntity.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空！");
        }
        //2. 数据库查找账号密码是否正确
        String newPassWord = MD5Util.MD5(password);
        UserEntity user = memberMapper.login(username, newPassWord);
        if (user == null) {
            return setResultError("账号或者密码不正确！");
        }
        //3. 如果正确，对应生成token
        String memberToken = TokenUtils.getMemberToken();
        //4.存放在redis中 key为token value 为userid
        Integer userEntityId = user.getId();
        log.info(memberToken,"用户信息token存放在redis中....key为:{},value",userEntityId);
        baseRedisService.setString(memberToken,userEntityId+"",Constants.TOKEN_MEMBER_TIME);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("memberToken",memberToken);
        //5.直接返回token
        return setResultSuccess(jsonObject);
    }

    /**
     * 通过token查找用户信息
     *
     * @param token 标识
     * @return token
     */
    @Override
    public ResponseBase findByTokenUser(@RequestParam("token") String token) {
        // 1.验证参数
        if (StringUtils.isEmpty(token)) {
            return setResultError("token不能为空!");
        }
        // 2.从redis中 使用token 查找对应 userid
        String strUserId = (String) baseRedisService.getString(token);
        if (StringUtils.isEmpty(strUserId)) {
            return setResultError("token无效或者已经过期!");
        }
        // 3.使用userid数据库查询用户信息返回给客户端
        Long userId = Long.parseLong(strUserId);
        UserEntity userEntity = memberMapper.findById(userId);
        if (userEntity == null) {
            return setResultError("为查找到该用户信息");
        }
        userEntity.setPassword(null);
        return setResultSuccess(userEntity);
    }

    private String message(String email) {
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", Constants.MSG_EMAIL);
        JSONObject content = new JSONObject();
        content.put("email", email);
        root.put("header", header);
        root.put("content", content);
        return root.toJSONString();
    }

    private void sendMsg(String json) {
        ActiveMQQueue activeMqQueue = new ActiveMQQueue(MESSAGESQUEUE);
        registerMailboxProducer.sendMsg(activeMqQueue, json);
    }

}
