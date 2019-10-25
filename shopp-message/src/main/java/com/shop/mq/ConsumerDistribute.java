package com.shop.mq;

import com.alibaba.fastjson.JSONObject;
import com.shop.adapter.MessageAdapter;
import com.shop.constant.Constants;
import com.shop.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author admin
 */
@Component
@Slf4j
public class ConsumerDistribute {
    @Autowired
    private EmailService emailService;
    private MessageAdapter messageAdapter;

    @JmsListener(destination = "messages_queue")
    public void distribute(String json) {
        log.info("======消息服务平台接收消息内容：" + json);
        if (StringUtils.isEmpty(json)) {
            return;
        }
        JSONObject rootJson = new JSONObject().parseObject(json);
        JSONObject header = rootJson.getJSONObject("header");
        String interfaceType = header.getString("interfaceType");
        if (StringUtils.isEmpty(interfaceType)) {
            return;
        }
        //判断接口类型是否是发邮件
        if (interfaceType.equals(Constants.MSG_EMAIL)) {
            messageAdapter=emailService;
        }
        if(messageAdapter==null){
            return;
        }

        JSONObject contentJson = rootJson.getJSONObject("content");
        messageAdapter.sendMes(contentJson);

    }
}
