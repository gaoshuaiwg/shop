package com.shop.email.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.shop.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author admin
 */
@Service
@Slf4j
public class EmailService implements MessageAdapter {
    @Value("${msg.subject}")
    private  String subject;
    @Value("${msg.text}")
    private  String  text;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendMes(JSONObject body) {
        String email = body.getString("email");
        if (StringUtils.isEmpty(email)){
            return;
        }
        log.info("信息服务平台发送邮件："+email);
        SimpleMailMessage message = new SimpleMailMessage();
        log.info("信息平台发送邮件开始");
        //来自账号
        message.setFrom(email);
        //发送账号
        message.setTo("782454480@qq.com");
        //标题
        message.setSubject(subject);
        //内容
        message.setText(text);
        javaMailSender.send(message);
        log.info("信息平台发送邮件完成");
    }
}
